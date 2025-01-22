package com.northcoders.find_my_escape_frontend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.find_my_escape_frontend.model.Beach;
import com.northcoders.find_my_escape_frontend.model.BeachRepository;
import com.northcoders.find_my_escape_frontend.model.Default;
import com.northcoders.find_my_escape_frontend.model.DefaultRepository;
import com.northcoders.find_my_escape_frontend.model.Museum;
import com.northcoders.find_my_escape_frontend.model.MuseumRepository;
import com.northcoders.find_my_escape_frontend.model.Nature;
import com.northcoders.find_my_escape_frontend.model.NatureRepository;
import com.northcoders.find_my_escape_frontend.model.Restaurant;
import com.northcoders.find_my_escape_frontend.model.RestaurantRepository;
import com.northcoders.find_my_escape_frontend.model.Sport;
import com.northcoders.find_my_escape_frontend.model.SportRepository;

import java.util.List;

public class DestinationViewModel extends AndroidViewModel {


    BeachRepository beachRepository;
    DefaultRepository defaultRepository;
    MuseumRepository museumRepository;
    NatureRepository natureRepository;
    RestaurantRepository restaurantRepository;
    SportRepository sportRepository;

    public DestinationViewModel(@NonNull Application application) {
        super(application);
        this.beachRepository = new BeachRepository();
        this.defaultRepository = new DefaultRepository();
        this.museumRepository = new MuseumRepository();
        this.natureRepository = new NatureRepository();
        this.restaurantRepository = new RestaurantRepository();
        this.sportRepository = new SportRepository();
    }

    public LiveData<List<Beach>> getBeaches(String place) {
        return beachRepository.getMutableLiveData(place);
    }

    public LiveData<List<Default>> getNightlife(String place) {
        return defaultRepository.getNightlifeMutableLiveData(place);
    }

    public LiveData<List<Default>> getCulture(String place) {
        return defaultRepository.getCultureMutableLiveData(place);
    }

    public LiveData<List<Museum>> getMuseums(String place) {
        return museumRepository.getMutableLiveData(place);
    }

    public LiveData<List<Default>> getAttractions(String place) {
        return defaultRepository.getAttractionsMutableLiveData(place);
    }

    public LiveData<List<Nature>> getNature(String place) {
        return natureRepository.getMutableLiveData(place);
    }

    public LiveData<List<Restaurant>> getRestaurants(String place) {
        return restaurantRepository.getMutableLiveData(place);
    }

    public LiveData<List<Sport>> getSport(String place) {
        return sportRepository.getMutableLiveData(place);
    }

}
