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

public class NatureRepository {

    private ArrayList<Nature> nature = new ArrayList<>();
    private MutableLiveData<List<Nature>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Nature>> getMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Nature>> call = placesApiService.getNature(place);

        call.enqueue(new Callback<List<Nature>>() {
            @Override
            public void onResponse(Call<List<Nature>> call, Response<List<Nature>> response) {
                List<Nature> nature = response.body();
                mutableLiveData.setValue(nature);
            }

            @Override
            public void onFailure(Call<List<Nature>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
