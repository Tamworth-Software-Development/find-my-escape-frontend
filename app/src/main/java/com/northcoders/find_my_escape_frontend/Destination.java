package com.northcoders.find_my_escape_frontend;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityDestinationBinding;
import com.northcoders.find_my_escape_frontend.model.Default;
import com.northcoders.find_my_escape_frontend.model.Sport;

import java.util.List;

public class Destination extends AppCompatActivity {

    private Spinner dropdown;
    private RecyclerView recyclerView;
    private ActivityDestinationBinding activityDestinationBinding;

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

//        List<Sport> sports = List.of(
//                new Sport("name", "leisure", "opening hours", "formatted", "web"),
//                new Sport("name", "leisure", "opening hours", "formatted", "web")
//        );
//
//        List<Default> defaults = List.of(
//                new Default("name", "opening hours", "formatted", "web"),
//                new Default("name", "opening hours", "formatted", "web")
//        );

        // Apply the adapter to the spinner
        dropdown.setAdapter(dropdownAdapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1: {
                        recyclerView.setAdapter(new BeachAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        break;
                    }

                    case 3: {
                        recyclerView.setAdapter(new MuseumAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        break;
                    }

                    case 5: {
                        recyclerView.setAdapter(new RestaurantAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        break;
                    }

                    case 6: {
                        recyclerView.setAdapter(new NatureAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        break;
                    }

                    case 7: {
                        recyclerView.setAdapter(new SportAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
                        break;
                    }
                    default: {
                        recyclerView.setAdapter(new DefaultAdapter());
                        recyclerView.setLayoutManager(new LinearLayoutManager(Destination.this));
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