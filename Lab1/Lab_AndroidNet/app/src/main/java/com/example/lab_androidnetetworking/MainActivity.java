package com.example.lab_androidnetetworking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.lab_androidnetetworking.adpter.PhotoAdapter;
import com.example.lab_androidnetetworking.api.PhotoApi;
import com.example.lab_androidnetetworking.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<PhotoModel> photoList;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        photoList = new ArrayList<>();
        adapter = new PhotoAdapter(photoList, this);
        recyclerView.setAdapter(adapter);

        loadPhotos();
    }

    private void loadPhotos() {
        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/") // Thay thế bằng URL API thực tế
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotoApi photoApi = retrofit.create(PhotoApi.class);
        Call<List<PhotoModel>> call = photoApi.getPhotos();

        call.enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                if (response.isSuccessful()) {
                    photoList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load photos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
