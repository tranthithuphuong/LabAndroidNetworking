package com.example.lab3.bai4.api;


import com.example.lab3.bai4.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoApi {
    @GET("photos") // Đặt URL API thực tế ở đây
    Call<List<Contact>> getPhotos();
}

