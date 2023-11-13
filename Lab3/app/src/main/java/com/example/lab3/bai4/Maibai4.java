package com.example.lab3.bai4;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.R;
import com.example.lab3.bai4.adpter.PhotoAdapter;
import com.example.lab3.bai4.api.PhotoApi;
import com.example.lab3.bai4.model.Contact;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Maibai4 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Contact> contactList;
    private PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai4);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        adapter = new PhotoAdapter(contactList, this);
        recyclerView.setAdapter(adapter);

        loadPhotos();
    }

    private void loadPhotos() {
        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102/Thondph16247_data.json") // Thay thế bằng URL API thực tế
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotoApi photoApi = retrofit.create(PhotoApi.class);
        Call<List<Contact>> call = photoApi.getPhotos();

        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    contactList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Toast.makeText(Maibai4.this, "Failed to load photos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
