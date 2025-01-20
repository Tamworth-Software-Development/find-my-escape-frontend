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

public class SportRepository {

    private ArrayList<Sport> sport = new ArrayList<>();
    private MutableLiveData<List<Sport>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Sport>> getMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Sport>> call = placesApiService.getSport(place);

        call.enqueue(new Callback<List<Sport>>() {
            @Override
            public void onResponse(Call<List<Sport>> call, Response<List<Sport>> response) {
                List<Sport> sport = response.body();
                mutableLiveData.setValue(sport);
            }

            @Override
            public void onFailure(Call<List<Sport>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
