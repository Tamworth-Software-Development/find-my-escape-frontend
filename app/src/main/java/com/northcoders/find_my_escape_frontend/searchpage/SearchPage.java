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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.northcoders.find_my_escape_frontend.Account;
import com.northcoders.find_my_escape_frontend.Destination;
import com.northcoders.find_my_escape_frontend.MainActivity;
import com.northcoders.find_my_escape_frontend.R;
import com.northcoders.find_my_escape_frontend.model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchPage extends AppCompatActivity {
    //Add functionality such that if the user is a guest then the favourited locations text and recycler view are not shown.
    private AutoCompleteTextView searchtext;
    private Button goButton;
    private FloatingActionButton accountSettingsButton, favouriteLocationsButton;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> locations = new ArrayList<>();
    private List<String> placeIds = new ArrayList<>();
    private List<String> cities = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<Location> favouriteLocations = new ArrayList<>();
    private String place_id;
    private String city;
    private String description;

    //Need to input your IP ADDRESS and PORT_NUMBER to connect to the backend.
    private final String YOUR_IP_ADDRESS = "";
    private final String PORT_NUMBER = "";

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace_id() {
        return place_id;
    }

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

        getFavouriteList();

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
                for (int i = 0; i < locations.size(); i++) {
                    if (locations.get(i).equals(searchtext.getText().toString())) {
                        place_id = placeIds.get(i);
//                        city = cities.get(i);
                    }
                }
                //Sends the city to the backend and gets the description for the city in return.
//                sendCityInfo(city);
                //Changes to the desired view.
                Intent intent = new Intent(v.getContext(), Destination.class);
                v.getContext().startActivity(intent);
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

    public void getLocationSuggestions(String s) {
        locations = new ArrayList<>();
        placeIds = new ArrayList<>();
        String url = "https://api.geoapify.com/v1/geocode/autocomplete?text=" + s + "&limit=5&apiKey=741c790467f44c29b744bbf236d0f337";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject locationNames = new JSONObject(new String(responseBody));
                    JSONArray array = locationNames.getJSONArray("features");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        JSONObject properties = object.getJSONObject("properties");
                        locations.add(properties.getString("formatted"));
                        placeIds.add(properties.getString("place_id"));
//                        cities.add(properties.getString("city"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                arrayAdapter = new ArrayAdapter<>(SearchPage.this, android.R.layout.simple_expandable_list_item_1, locations);
                searchtext.setAdapter(arrayAdapter);
                searchtext.showDropDown();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast toast = Toast.makeText(SearchPage.this, error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }

        });
    }

    public void getFavouriteList() {
        //Add logic to get the favouriteList from the backend API.
        //check url is right.
        String url = "http://" + YOUR_IP_ADDRESS + ":" + PORT_NUMBER + "/api/v1/favourite-locations";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray faveLocationNames = new JSONArray(new String(responseBody));
                    for (int i = 0; i < faveLocationNames.length(); i++) {
                        JSONObject location = faveLocationNames.getJSONObject(i);
                        String name = location.getString("name");
                        String description = location.getString("description");
                        String image = location.getString("image");
                        Location newLocation = new Location(name, description, image);
                        favouriteLocations.add(newLocation);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast toast = Toast.makeText(SearchPage.this, error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void sendCityInfo(String s) {
        //Add logic to send the city to the backend API.
        String url = "http://" + YOUR_IP_ADDRESS + ":" + PORT_NUMBER + "/api/v1/location/information/" + s;
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject wikiInfo = new JSONObject(new String(responseBody));
                    description = wikiInfo.getString("extract");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast toast = Toast.makeText(SearchPage.this, error.getMessage(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}