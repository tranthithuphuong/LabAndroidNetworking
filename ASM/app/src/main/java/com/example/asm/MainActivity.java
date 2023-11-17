package com.example.asm;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.adapter.BookAdapter;
import com.example.asm.api.api;
import com.example.asm.model.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BookAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Call the API to get the list of books
        callApi();
    }
    public void onAddButtonClick(View view) {
        // Chuyển sang màn hình thêm sách
        Intent intent = new Intent(this, AddBook.class);
        startActivityForResult(intent, 1); // Sử dụng startActivityForResult nếu bạn cần nhận kết quả từ màn hình thêm sách
    }
    private void callApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.198:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api apiService = retrofit.create(api.class);

        Call<List<Book>> call = apiService.getBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    List<Book> books = response.body();
                    if (books != null) {
                        // Update the data in the adapter and notify the RecyclerView
                        adapter.setData(books);
                    }
                } else {
                    Log.e("API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }
}
