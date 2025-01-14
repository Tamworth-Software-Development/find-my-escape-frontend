package com.northcoders.find_my_escape_frontend.service;

import com.northcoders.find_my_escape_frontend.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    //todo: check signup api route
    @POST("users/signup")
    Call<Void> signupUser(@Body User user);
}

