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

public class DefaultRepository {

    private ArrayList<Default> defaults = new ArrayList<>();
    private MutableLiveData<List<Default>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Default>> getNightlifeMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Default>> call = placesApiService.getNightlife(place);

        call.enqueue(new Callback<List<Default>>() {
            @Override
            public void onResponse(Call<List<Default>> call, Response<List<Default>> response) {
                List<Default> nightlife = response.body();
                mutableLiveData.setValue(nightlife);
            }

            @Override
            public void onFailure(Call<List<Default>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

    public MutableLiveData<List<Default>> getCultureMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Default>> call = placesApiService.getCulture(place);

        call.enqueue(new Callback<List<Default>>() {
            @Override
            public void onResponse(Call<List<Default>> call, Response<List<Default>> response) {
                List<Default> culture = response.body();
                mutableLiveData.setValue(culture);
            }

            @Override
            public void onFailure(Call<List<Default>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

    public MutableLiveData<List<Default>> getAttractionsMutableLiveData(String place) {
        PlacesApiService placesApiService = PlacesRetrofitInstance.getService();

        Call<List<Default>> call = placesApiService.getAttractions(place);

        call.enqueue(new Callback<List<Default>>() {
            @Override
            public void onResponse(Call<List<Default>> call, Response<List<Default>> response) {
                List<Default> attractions = response.body();
                mutableLiveData.setValue(attractions);
            }

            @Override
            public void onFailure(Call<List<Default>> call, Throwable t) {
                Log.e("GET request", t.getMessage(), t);
            }
        });

        return mutableLiveData;
    }

}
