package com.northcoders.find_my_escape_frontend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityDestinationBinding;
import com.northcoders.find_my_escape_frontend.model.Beach;
import com.northcoders.find_my_escape_frontend.model.Default;
import com.northcoders.find_my_escape_frontend.model.Museum;
import com.northcoders.find_my_escape_frontend.model.Nature;
import com.northcoders.find_my_escape_frontend.model.Restaurant;
import com.northcoders.find_my_escape_frontend.model.Sport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Destination extends AppCompatActivity {

    private TextView destination;
    private TextView description;
    private Spinner dropdown;
    private RecyclerView recyclerView;
    private ActivityDestinationBinding activityDestinationBinding;
    //DestinationViewModel viewModel;

    List<Beach> beaches;
    List<Default> defaults = new ArrayList<>();
    List<Museum> museums;
    List<Nature> nature;
    List<Restaurant> restaurants;
    List<Sport> sport;

    OkHttpClient client;
    MutableLiveData<List<Default>> defaultMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Beach>> beachMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Museum>> museumMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Restaurant>> restaurantMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Nature>> natureMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Sport>> sportMutableLiveData = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        activityDestinationBinding = DataBindingUtil.setContentView(this, R.layout.activity_destination);
        recyclerView = activityDestinationBinding.recyclerView;

        dropdown = findViewById(R.id.dropdown);

        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> dropdownAdapter = ArrayAdapter.createFromResource(this, R.array.activity_array, android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears
        dropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //viewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

        destination = findViewById(R.id.destination);
        description = findViewById(R.id.description);

        String placeName = "paris";

        destination.setText(placeName);

        client = new OkHttpClient();

        //String descriptionUrl = String.format("https://en.wikipedia.org/w/api.php?action=query&titles=%s&format=json&prop=pageimages|extracts&formatversion=2&piprop=original&pithumbsize=1000&pilicense=free&exchars=250&exintro=1&explaintext=1", placeName);

        String descriptionUrl = String.format("http://10.0.2.2:8080/api/v1/location/information/%s", placeName);

        Request request = new Request.Builder().url(descriptionUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                description.setText("No description available");

                Log.e("GET", "get description failed", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String descriptionText = "";

                        try {
                            JSONObject data = new JSONObject(response.body().string());
                            //JSONObject page = data.getJSONObject("query").getJSONArray("pages").getJSONObject(0);

                            if (data.has("extract")) {
                                descriptionText = data.getString("extract");
                            }

                        } catch (JSONException | IOException e) {
                            Log.e("Inner GET", "Exception during GET description call", e);
                        }

                        if (descriptionText.isEmpty()) {
                            descriptionText = "No description available";
                        }

                        description.setText(descriptionText);

                    }
                });
            }
        });

//        System.out.println("ABOVE");
//
//        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=adult.nightclub,catering.bar&filter=place:5184680822e6551340590dd87a2bb7e14640f00103f9017672bf6f00000000c00203";
//        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                System.out.println("above the try but in the onSuccess");
//                try {
//                    JSONObject jsonObject = new JSONObject(new String(responseBody));
//                    System.out.println(jsonObject.toString());
//                    JSONArray jsonArray = jsonObject.getJSONArray("features");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);
//                        JSONObject properties = jsonObject2.getJSONObject("properties");
//
//                    }
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                //Cry
//                System.out.println("FAILED");
//            }
//        });


        String place = "place:5184680822e6551340590dd87a2bb7e14640f00103f9017672bf6f00000000c00203";

        // Apply the adapter to the spinner
        dropdown.setAdapter(dropdownAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {

                        List<Default> defaultList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=adult.nightclub,catering.bar&filter=".concat(place);

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get nightlife activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Default defaultObject = new Default(name,
                                                openingHours,
                                                formatted,
                                                website);

                                        defaultList.add(defaultObject);
                                    }

                                    defaultMutableLiveData.postValue(defaultList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        defaultMutableLiveData.observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;

                                DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                                recyclerView.setAdapter(defaultAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                defaultAdapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    }

                    case 1: {
                        List<Beach> beachList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=beach&filter=".concat(place);

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get beach activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String restrictions;
                                        if (properties.has("restrictions")) {
                                            restrictions = properties.getString("restrictions");
                                        } else {
                                            restrictions = "no restrictions!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        Beach beachObject = new Beach(name,
                                                restrictions,
                                                formatted);

                                        beachList.add(beachObject);
                                    }

                                    beachMutableLiveData.postValue(beachList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        beachMutableLiveData.observe(Destination.this, new Observer<List<Beach>>() {
                            @Override
                            public void onChanged(List<Beach> beachesList) {
                                beaches = beachesList;

                                BeachAdapter beachAdapter = new BeachAdapter(beaches);
                                recyclerView.setAdapter(beachAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                beachAdapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    }

                    case 2: {
                        List<Default> defaultList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=entertainment.culture&filter=".concat(place);

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get culture activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Default defaultObject = new Default(name,
                                                openingHours,
                                                formatted,
                                                website);

                                        defaultList.add(defaultObject);
                                    }

                                    defaultMutableLiveData.postValue(defaultList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        defaultMutableLiveData.observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;

                                DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                                recyclerView.setAdapter(defaultAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                defaultAdapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    }

                    case 3: {
                        List<Museum> museumList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=entertainment.museum&filter=".concat(place);

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get museum activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String fee;
                                        if (properties.has("fee")) {
                                            fee = properties.getString("fee");
                                        } else {
                                            fee = "no fee!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Museum museumObject = new Museum(name,
                                                fee,
                                                openingHours,
                                                formatted,
                                                website);

                                        museumList.add(museumObject);
                                    }

                                    museumMutableLiveData.postValue(museumList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        museumMutableLiveData.observe(Destination.this, new Observer<List<Museum>>() {
                            @Override
                            public void onChanged(List<Museum> museumsList) {
                                museums = museumsList;

                                MuseumAdapter museumAdapter = new MuseumAdapter(museums);
                                recyclerView.setAdapter(museumAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                museumAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                    }

                    case 4: {
                        List<Default> defaultList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=tourism.attraction,tourism.sights&filter=".concat(place);


                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get tourist attraction activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Default defaultObject = new Default(name,
                                                openingHours,
                                                formatted,
                                                website);

                                        defaultList.add(defaultObject);
                                    }

                                    defaultMutableLiveData.postValue(defaultList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        defaultMutableLiveData.observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;

                                DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                                recyclerView.setAdapter(defaultAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                defaultAdapter.notifyDataSetChanged();
                            }
                        });
                        break;
                    }

                    case 5: {
                        List<Restaurant> restaurantList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=catering.restaurant&filter=".concat(place);

                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get restaurant activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String cuisine;
                                        if (properties.has("cuisine")) {
                                            cuisine = properties.getString("cuisine");
                                        } else {
                                            cuisine = "no cuisine!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Restaurant retaurantObject = new Restaurant(name,
                                                cuisine,
                                                openingHours,
                                                formatted,
                                                website);

                                        restaurantList.add(retaurantObject);
                                    }

                                    restaurantMutableLiveData.postValue(restaurantList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        restaurantMutableLiveData.observe(Destination.this, new Observer<List<Restaurant>>() {
                            @Override
                            public void onChanged(List<Restaurant> restaurantsList) {
                                restaurants = restaurantsList;

                                RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurants);
                                recyclerView.setAdapter(restaurantAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                restaurantAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                    }

                    case 6: {
                        List<Nature> natureList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=natural&filter=".concat(place);


                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get nature activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Nature natureObject = new Nature(name,
                                                formatted,
                                                website);

                                        natureList.add(natureObject);
                                    }

                                    natureMutableLiveData.postValue(natureList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        natureMutableLiveData.observe(Destination.this, new Observer<List<Nature>>() {
                            @Override
                            public void onChanged(List<Nature> naturesList) {
                                nature = naturesList;

                                NatureAdapter natureAdapter = new NatureAdapter(nature);
                                recyclerView.setAdapter(natureAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                natureAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                    }

                    case 7: {
                        List<Sport> sportList = new ArrayList<>();

                        String url = "https://api.geoapify.com/v2/places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=sport&filter=".concat(place);


                        Request request = new Request.Builder()
                                .url(url)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("GET", "Get sport activities failed", e);
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                                try {
                                    JSONObject data = new JSONObject(response.body().string());
                                    JSONArray jsonArray = data.getJSONArray("features");

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject feature = jsonArray.getJSONObject(i);
                                        JSONObject properties = feature.getJSONObject("properties");

                                        String name;
                                        if (properties.has("name")) {
                                            name = properties.getString("name");
                                        } else {
                                            name = "no name!";
                                        }

                                        String leisure;
                                        if (properties.has("leisure")) {
                                            leisure = properties.getString("leisure");
                                        } else {
                                            leisure = "no leisure!";
                                        }

                                        String openingHours;
                                        if (properties.has("opening_hours")) {
                                            openingHours = properties.getString("opening_hours");
                                        } else {
                                            openingHours = "no opening hours!";
                                        }

                                        String formatted;
                                        if (properties.has("formatted")) {
                                            formatted = properties.getString("formatted");
                                        } else {
                                            formatted = "address";
                                        }

                                        String website;
                                        if (properties.has("website")) {
                                            website = properties.getString("website");
                                        } else {
                                            website = "no website";
                                        }

                                        Sport sportObject = new Sport(name,
                                                leisure,
                                                openingHours,
                                                formatted,
                                                website);

                                        sportList.add(sportObject);
                                    }

                                    sportMutableLiveData.postValue(sportList);

                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new IOException(e);
                                }

                            }
                        });

                        sportMutableLiveData.observe(Destination.this, new Observer<List<Sport>>() {
                            @Override
                            public void onChanged(List<Sport> sportsList) {
                                sport = sportsList;

                                SportAdapter sportAdapter = new SportAdapter(sport);
                                recyclerView.setAdapter(sportAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                                sportAdapter.notifyDataSetChanged();
                            }
                        });

                        break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });


    }
}