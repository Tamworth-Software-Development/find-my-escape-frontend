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

    private List<String> locations;
    private List<String> placeIds;
    private List<Location> favouriteLocations;

    public List<Location> getFavouriteLocations() {
        return favouriteLocations;
    }

    public void setFavouriteLocations(List<Location> favouriteLocations) {
        this.favouriteLocations = favouriteLocations;
    }

    public LocationRepository() {}

    public void getLocationSuggestions(String s) {
        String url = "https://api.geoapify.com/v1/geocode/autocomplete?text=" + s + "&limit=5&apiKey=741c790467f44c29b744bbf236d0f337";
        new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject locationNames = new JSONObject(new String(responseBody));
                    JSONArray array = locationNames.getJSONArray("features");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        JSONObject properties = object.getJSONObject("properties");
                        locations.add(properties.getString("formatted"));
                        placeIds.add(properties.getString("place_id"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }

        });
    }

    public void getFavouritesList() {
        String favourites_url = ""; //enter correct url here
        favouriteLocations = new ArrayList<>();
        new AsyncHttpClient().get(favourites_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject JSONfavouriteLocations = new JSONObject(new String(responseBody));
                    //Work out the response body and what JSONObjects / Arrays may be needed.
                    for (int i = 0; i < JSONfavouriteLocations.length(); i++) {
                        favouriteLocations.add(new Location(JSONfavouriteLocations.getString("name"), JSONfavouriteLocations.getString("description")));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {}
        });
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(List<String> placeIds) {
        this.placeIds = placeIds;
    }
}