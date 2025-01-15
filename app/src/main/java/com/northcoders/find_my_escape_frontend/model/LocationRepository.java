package com.northcoders.find_my_escape_frontend.model;

import android.app.Application;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.northcoders.find_my_escape_frontend.searchpage.SearchPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LocationRepository {
    private MutableLiveData<List<Location>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public LocationRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Location>> getMutableLiveData(){
        String favourites_url = ""; //enter correct url here
        List<Location> favouritesList = new ArrayList<>();
        new AsyncHttpClient().get(favourites_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject favouriteLocations = new JSONObject(new String(responseBody));
                    //Work out the response body and what JSONObjects / Arrays may be needed.
                    for (int i = 0; i < favouriteLocations.length(); i++){
                        favouritesList.add(new Location(favouriteLocations.getString("name"), favouriteLocations.getString("description")));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                mutableLiveData.setValue(favouritesList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
        return mutableLiveData;
    }
}
