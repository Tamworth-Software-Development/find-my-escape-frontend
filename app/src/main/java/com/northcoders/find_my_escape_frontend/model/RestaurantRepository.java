package com.northcoders.find_my_escape_frontend.model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.northcoders.find_my_escape_frontend.service.PlacesApiService;
import com.northcoders.find_my_escape_frontend.service.PlacesRetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantRepository {

    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private MutableLiveData<List<Restaurant>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Restaurant>> getMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Restaurant>> call = placesApiService.getRestaurants(place);

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> restaurants = response.body();
                mutableLiveData.setValue(restaurants);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
