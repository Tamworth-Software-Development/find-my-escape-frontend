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

public class BeachRepository {

    private ArrayList<Beach> beaches = new ArrayList<>();
    private MutableLiveData<List<Beach>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Beach>> getMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Beach>> call = placesApiService.getBeaches(place);

        call.enqueue(new Callback<List<Beach>>() {
            @Override
            public void onResponse(Call<List<Beach>> call, Response<List<Beach>> response) {
                List<Beach> beaches = response.body();
                mutableLiveData.setValue(beaches);
            }

            @Override
            public void onFailure(Call<List<Beach>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
