package com.example.lab_androidnetetworking.api;

import com.example.lab_androidnetetworking.model.PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotoApi {
    @GET("photos") // Đặt URL API thực tế ở đây
    Call<List<PhotoModel>> getPhotos();
}

