package com.northcoders.find_my_escape_frontend.service;

import com.northcoders.find_my_escape_frontend.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    //todo: check signup api route
    @POST("users/signup")
    Call<Void> signupUser(@Body User user);

    @GET("user/{uid}")
    Call<User> getUser(@Path("uid") String uid);

    @PUT("user/{uid}")
    Call<Void> updateUser(@Path("uid") String uid, @Body User user);
}

