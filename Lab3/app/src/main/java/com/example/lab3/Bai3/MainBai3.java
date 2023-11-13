package com.example.lab3.Bai3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.lab3.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainBai3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai3);
        initViews();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recyler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://65502d397d203ab6626d9c64.mockapi.io/users/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JSONResponse jsonResponse = response.body();
                    AndroidVersion[] androidVersions = jsonResponse.getAndroid();
                    if (androidVersions != null && androidVersions.length > 0) {
                        ArrayList<AndroidVersion> androidList = new ArrayList<>(Arrays.asList(androidVersions));
                        DataAdapter adapter = new DataAdapter(androidList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // Xử lý khi danh sách AndroidVersion trả về rỗng
                    }
                } else {
                    // Xử lý khi response không thành công hoặc response.body() trả về null
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }

        });
    }
}