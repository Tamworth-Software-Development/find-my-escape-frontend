package com.northcoders.find_my_escape_frontend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.northcoders.find_my_escape_frontend.model.User;
import com.northcoders.find_my_escape_frontend.service.ApiService;
import com.northcoders.find_my_escape_frontend.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestAccount extends AppCompatActivity {

    EditText nameEditText, emailEditText, passwordEditText, passwordConfirmEditText;
    Button signupButton, loginPageButton;
    FirebaseAuth mAuth;

    FirebaseUser firebaseUser;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else if (currentUser.getEmail() != null) {
            Intent intent = new Intent(getApplicationContext(), Account.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_acoount);

        mAuth = FirebaseAuth.getInstance();
        nameEditText = findViewById(R.id.editTextNameSignup);
        emailEditText = findViewById(R.id.editTextEmailSignup);
        passwordEditText = findViewById(R.id.editTextPasswordSignup);
        passwordConfirmEditText = findViewById(R.id.editTextConfirmPassword);
        signupButton = findViewById(R.id.signupButton);
        loginPageButton = findViewById(R.id.buttonGoToLogin);
        firebaseUser = mAuth.getCurrentUser();


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = passwordConfirmEditText.getText().toString().trim();

                if (name.isEmpty()) {
                    nameEditText.setError("Name is required");
                    nameEditText.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please enter a valid email");
                    emailEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordEditText.setError("Password must be at least 6 characters");
                    passwordEditText.requestFocus();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    passwordConfirmEditText.setError("Passwords do not match");
                    passwordConfirmEditText.requestFocus();
                    return;
                }

                if (firebaseUser != null && firebaseUser.getEmail() == null) {
                    AuthCredential credential = EmailAuthProvider.getCredential(email, password);
                    firebaseUser.linkWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = task.getResult().getUser();

                                        String uid = firebaseUser != null ? firebaseUser.getUid() : null;

                                        ApiService apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
                                        User user = new User(uid, email, name);
                                        apiService.signupUser(user).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(GuestAccount.this, "User registered in backend!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(GuestAccount.this, "Failed to register user in backend.", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(GuestAccount.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Toast.makeText(GuestAccount.this, "Account Created", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(GuestAccount.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        loginPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });
    }
}