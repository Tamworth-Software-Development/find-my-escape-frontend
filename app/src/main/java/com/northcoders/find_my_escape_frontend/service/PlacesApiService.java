package com.northcoders.find_my_escape_frontend.service;

import com.northcoders.find_my_escape_frontend.model.Beach;
import com.northcoders.find_my_escape_frontend.model.Default;
import com.northcoders.find_my_escape_frontend.model.Museum;
import com.northcoders.find_my_escape_frontend.model.Nature;
import com.northcoders.find_my_escape_frontend.model.Restaurant;
import com.northcoders.find_my_escape_frontend.model.Sport;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApiService {

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=adult")
    Call<List<Default>> getNightlife(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=beach")
    Call<List<Beach>> getBeaches(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=entertainment.culture")
    Call<List<Default>> getCulture(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=entertainment.museum")
    Call<List<Museum>> getMuseums(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=tourism.attraction")
    Call<List<Default>> getAttractions(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=natural")
    Call<List<Nature>> getNature(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=catering.restaurant")
    Call<List<Restaurant>> getRestaurants(@Query("filter") String place);

    @GET("places?apiKey=b12260ebfdd0467bbebc0fefe6930dc9&categories=sport")
    Call<List<Sport>> getSport(@Query("filter") String place);

}
