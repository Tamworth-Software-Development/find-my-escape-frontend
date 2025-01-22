package com.northcoders.find_my_escape_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.northcoders.find_my_escape_frontend.databinding.ActivityAccountBinding;
import com.northcoders.find_my_escape_frontend.model.User;
import com.northcoders.find_my_escape_frontend.searchpage.SearchPage;
import com.northcoders.find_my_escape_frontend.service.ApiService;
import com.northcoders.find_my_escape_frontend.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Account extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button updateInfoButton, changePasswordButton, logoutButton, deleteAccountButton;
    FloatingActionButton backButton;
    EditText editTextName, editTextEmail, editTextNewPassword, editTextNewPasswordConfirm;
    FirebaseUser firebaseUser;
    User user;
    ActivityAccountBinding binding;


    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else if (currentUser.getEmail() == null) {
            Intent intent = new Intent(getApplicationContext(), GuestAccount.class);
            startActivity(intent);
            finish();
        }
    }

    private void fetchUser(String uid) {
        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        apiService.getUser(uid).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    binding.setUser(response.body());
                } else {
                    Toast.makeText(Account.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Account.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String uid, String updatedName, String updatedEmail) {
        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
        User updatedUser = new User(uid, updatedEmail, updatedName);

        apiService.updateUser(uid, updatedUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Account.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Account.this, "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Account.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        logoutButton = binding.logoutButton;
        editTextName = binding.editTextName;
        editTextEmail = binding.editTextEmail;
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewPasswordConfirm = findViewById(R.id.editTextNewPasswordConfirm);
        updateInfoButton = findViewById(R.id.updateInfoButton);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        deleteAccountButton = findViewById(R.id.deleteAccountButton);
        backButton = findViewById(R.id.backButton);


        user = new User();
        binding.setUser(user);
        if (firebaseUser != null) {
            fetchUser(firebaseUser.getUid());
        }
        updateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedName = editTextName.getText().toString().trim();
                String updatedEmail = editTextEmail.getText().toString().trim();

                if (TextUtils.isEmpty(updatedName)) {
                    editTextName.setError("Name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(updatedEmail) || !android.util.Patterns.EMAIL_ADDRESS.matcher(updatedEmail).matches()) {
                    editTextEmail.setError("Enter a valid email");
                    return;
                }
                updateUser(firebaseUser.getUid(), updatedName, updatedEmail);

                if(!updatedEmail.equals(firebaseUser.getEmail())) {
                    firebaseUser.updateEmail(updatedEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Account.this, "User email address updated.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = editTextNewPassword.getText().toString().trim();
                String newPasswordConfirm = editTextNewPasswordConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword)) {
                    editTextNewPassword.setError("Password is required");
                    return;
                }

                if (newPassword.length() < 6) {
                    editTextNewPassword.setError("Password must be at least 6 characters");
                    return;
                }

                if (!newPassword.equals(newPasswordConfirm)) {
                    editTextNewPasswordConfirm.setError("Passwords do not match");
                    editTextNewPasswordConfirm.requestFocus();
                    return;
                }

                // todo: re-authenticate user with current password before updating

                firebaseUser.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Account.this, "User password updated.", Toast.LENGTH_SHORT).show();

                                    binding.editTextNewPassword.setText("");
                                    binding.editTextNewPasswordConfirm.setText("");

                                } else {
                                    Toast.makeText(Account.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Account.this)
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            firebaseUser.delete()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Account.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(Account.this, "Failed to delete account: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        })
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPage.class);
                startActivity(intent);
                finish();
            }
        });

    }
}