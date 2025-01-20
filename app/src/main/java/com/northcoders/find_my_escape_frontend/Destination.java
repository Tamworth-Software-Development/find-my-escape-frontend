package com.northcoders.find_my_escape_frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

import java.util.List;

public class Destination extends AppCompatActivity {

    private Spinner dropdown;
    private RecyclerView recyclerView;
    private ActivityDestinationBinding activityDestinationBinding;
    DestinationViewModel viewModel;
    List<Beach> beaches;
    List<Default> defaults;
    List<Museum> museums;
    List<Nature> nature;
    List<Restaurant> restaurants;
    List<Sport> sport;

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

        viewModel = new ViewModelProvider(this).get(DestinationViewModel.class);

//        List<Sport> sports = List.of(
//                new Sport("name", "leisure", "opening hours", "formatted", "web"),
//                new Sport("name", "leisure", "opening hours", "formatted", "web")
//        );
//
//        List<Default> defaults = List.of(
//                new Default("name", "opening hours", "formatted", "web"),
//                new Default("name", "opening hours", "formatted", "web")
//        );

        String place = "place:5184680822e6551340590dd87a2bb7e14640f00103f9017672bf6f00000000c00203";

        // Apply the adapter to the spinner
        dropdown.setAdapter(dropdownAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        viewModel.getNightlife(place).observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;
                            }
                        });
                        DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                        recyclerView.setAdapter(defaultAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        defaultAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 1: {
                        viewModel.getBeaches(place).observe(Destination.this, new Observer<List<Beach>>() {
                            @Override
                            public void onChanged(List<Beach> beachesList) {
                                beaches = beachesList;
                            }
                        });
                        BeachAdapter beachAdapter = new BeachAdapter(beaches);
                        recyclerView.setAdapter(beachAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        beachAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 2: {
                        viewModel.getCulture(place).observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;
                            }
                        });
                        DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                        recyclerView.setAdapter(defaultAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        defaultAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 3: {
                        viewModel.getMuseums(place).observe(Destination.this, new Observer<List<Museum>>() {
                            @Override
                            public void onChanged(List<Museum> museumsList) {
                                museums = museumsList;
                            }
                        });
                        MuseumAdapter museumAdapter = new MuseumAdapter(museums);
                        recyclerView.setAdapter(museumAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        museumAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 4: {
                        viewModel.getAttractions(place).observe(Destination.this, new Observer<List<Default>>() {
                            @Override
                            public void onChanged(List<Default> defaultsList) {
                                defaults = defaultsList;
                            }
                        });
                        DefaultAdapter defaultAdapter = new DefaultAdapter(defaults);
                        recyclerView.setAdapter(defaultAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        defaultAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 5: {
                        viewModel.getRestaurants(place).observe(Destination.this, new Observer<List<Restaurant>>() {
                            @Override
                            public void onChanged(List<Restaurant> restaurantsList) {
                                restaurants = restaurantsList;
                            }
                        });
                        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurants);
                        recyclerView.setAdapter(restaurantAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        restaurantAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 6: {
                        viewModel.getNature(place).observe(Destination.this, new Observer<List<Nature>>() {
                            @Override
                            public void onChanged(List<Nature> naturesList) {
                                nature = naturesList;
                            }
                        });
                        NatureAdapter natureAdapter = new NatureAdapter(nature);
                        recyclerView.setAdapter(natureAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        natureAdapter.notifyDataSetChanged();
                        break;
                    }

                    case 7: {
                        viewModel.getSport(place).observe(Destination.this, new Observer<List<Sport>>() {
                            @Override
                            public void onChanged(List<Sport> sportsList) {
                                sport = sportsList;
                            }
                        });
                        SportAdapter sportAdapter = new SportAdapter(sport);
                        recyclerView.setAdapter(sportAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        sportAdapter.notifyDataSetChanged();
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