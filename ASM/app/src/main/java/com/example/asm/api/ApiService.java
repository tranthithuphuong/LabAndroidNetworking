package com.example.asm.api;


import com.example.asm.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("user")
    Call<List<User>> getUsers();

    @POST("user") // Endpoint của API đăng ký
    Call<User> registerUser(@Body User user);
}

