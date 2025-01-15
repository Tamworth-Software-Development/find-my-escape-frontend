package com.northcoders.find_my_escape_frontend.searchpage;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.northcoders.find_my_escape_frontend.MainActivity;
import com.northcoders.find_my_escape_frontend.R;
import com.northcoders.find_my_escape_frontend.databinding.LocationViewBinding;
import com.northcoders.find_my_escape_frontend.model.Location;
import com.northcoders.find_my_escape_frontend.model.LocationRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchPage extends AppCompatActivity {
//Add functionality such that if the user is a guest then the favourited locations and recycler view are not shown.
    private AutoCompleteTextView searchtext;
    private Button goButton;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> locations = new ArrayList<>();
    private List<String> placeIds = new ArrayList<>();
    private RecyclerView recyclerView;
    private MutableLiveData<List<Location>> mutableLiveData = new MutableLiveData<>();
    private LocationViewBinding binding;
    private SearchPageViewModel model;
    private ArrayList<Location> favouriteLocations;
    private LocationAdapter adapter;


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
        LocationRepository locationRepository = new LocationRepository(new Application());
        locationRepository.getMutableLiveData();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_page);
        model = new ViewModelProvider(this).get(SearchPageViewModel.class);

        recyclerView = findViewById(R.id.searchRecyclerView);
        searchtext = findViewById(R.id.searchtext);
        goButton = findViewById(R.id.gobutton);
        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
                getLocationSuggestions(str);
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
                for (int i = 0; i < locations.size(); i++){
                    if (locations.get(i).equals(searchtext.getText().toString())){
                        String formatted = locations.get(i);
                        String place_id = placeIds.get(i);
                        // call update method i.e updateUser(int userId, String formatted, String place_id)
                        // Change the view using intent to the destination view.
                    }
                }
            }
        });
    }


    public void getLocationSuggestions(String s){
        locations = new ArrayList<>();
        String url = "https://api.geoapify.com/v1/geocode/autocomplete?text=" +s+ "&limit=5&apiKey=741c790467f44c29b744bbf236d0f337";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject locationNames = new JSONObject(new String(responseBody));
                    JSONArray array = locationNames.getJSONArray("features");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        JSONObject properties = object.getJSONObject("properties");
                        locations.add(properties.getString("formatted"));
                        placeIds.add(properties.getString("place_id"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                arrayAdapter = new ArrayAdapter<>(SearchPage.this, android.R.layout.simple_expandable_list_item_1, locations);
                searchtext.setAdapter(arrayAdapter);
                searchtext.showDropDown();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    public void getAllLocations(){
        model.getRecyclerViewData().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                favouriteLocations = (ArrayList<Location>) locations;
                displayInRecyclerView();
            }
        });
    }

    public void displayInRecyclerView(){
        adapter = new LocationAdapter(favouriteLocations, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();
    }


}