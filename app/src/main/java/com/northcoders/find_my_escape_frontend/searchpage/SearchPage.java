package com.northcoders.find_my_escape_frontend.searchpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.northcoders.find_my_escape_frontend.Account;
import com.northcoders.find_my_escape_frontend.MainActivity;
import com.northcoders.find_my_escape_frontend.R;
import com.northcoders.find_my_escape_frontend.model.Location;
import com.northcoders.find_my_escape_frontend.model.LocationRepository;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchPage extends AppCompatActivity {
    //Add functionality such that if the user is a guest then the favorited locations text and recycler view are not shown.
    private AutoCompleteTextView searchtext;
    private Button goButton;
    FloatingActionButton accountSettingsButton, favouriteLocationsButton;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> locations = new ArrayList<>();
    private List<String> placeIds = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Location> favouriteLocations;
    private SearchPageClickHandler handler;
    private LocationRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        favouriteLocations = List.of(new Location("Barcelona", "Barcelona is a wonderful city in spain"),
                new Location("London", "London is the capital of England and is a very famous and historic city"));
        //This list will be retrieved from the backend API endpoint. Add logic to retrieve this.
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new LocationAdapter(favouriteLocations, this));
        accountSettingsButton = findViewById(R.id.accountSettingsButton);
        favouriteLocationsButton = findViewById(R.id.favouriteLocationsButton);
        searchtext = findViewById(R.id.searchtext);
        goButton = findViewById(R.id.gobutton);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                repository.getLocationSuggestions(str);
                arrayAdapter = new ArrayAdapter<>(SearchPage.this, android.R.layout.simple_expandable_list_item_1, repository.getLocations());
                searchtext.setAdapter(arrayAdapter);
                searchtext.showDropDown();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        searchtext.setOnDismissListener(new AutoCompleteTextView.OnDismissListener() {
            @Override
            public void onDismiss() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), 0);
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logic to send an update request to the backend API and to change the view.
                //Method that contains this logic.
                handler.goButtonClicked(repository.getLocations(), repository.getPlaceIds(), searchtext.getText().toString());
            }
        });

        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
                finish();
            }
        });

        favouriteLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Account.class);
                startActivity(intent);
                finish();
            }
        });
    }


}