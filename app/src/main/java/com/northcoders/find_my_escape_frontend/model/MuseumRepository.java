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

public class MuseumRepository {

    private ArrayList<Museum> museums = new ArrayList<>();
    private MutableLiveData<List<Museum>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Museum>> getMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Museum>> call = placesApiService.getMuseums(place);

        call.enqueue(new Callback<List<Museum>>() {
            @Override
            public void onResponse(Call<List<Museum>> call, Response<List<Museum>> response) {
                List<Museum> museums = response.body();
                mutableLiveData.setValue(museums);
            }

            @Override
            public void onFailure(Call<List<Museum>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
