package com.northcoders.find_my_escape_frontend.searchpage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.northcoders.find_my_escape_frontend.model.Location;
import com.northcoders.find_my_escape_frontend.model.LocationRepository;

import java.util.List;

public class SearchPageViewModel extends AndroidViewModel {

    private LocationRepository repository;
    public SearchPageViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Location>> getRecyclerViewData(){
        return repository.getMutableLiveData();
    }
}
