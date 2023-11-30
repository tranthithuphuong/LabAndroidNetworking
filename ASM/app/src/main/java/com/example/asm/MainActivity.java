package com.example.asm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.adapter.BookAdapter;
import com.example.asm.api.api;
import com.example.asm.model.Book;
import com.example.asm.model.User;

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
    private Button addButton;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BookAdapter(this, new ArrayList<>(), userRole);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        addButton = findViewById(R.id.addButton);

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý đăng xuất, chuyển đến màn hình đăng nhập và xóa hết các màn hình khác
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // Lấy trạng thái quyền từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userRole")) {
            userRole = intent.getStringExtra("userRole");
            adapter.setUserRole(userRole);
        }
        saveUserRole(userRole); // Lưu vào SharedPreferences
        // Call the API to get the list of books
        callApi();

        // Kiểm tra quyền và ẩn nút tương ứng
        checkUserRole();

        adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Lấy sách tại vị trí được chọn
                Book selectedBook = adapter.getItem(position);

                // Chuyển đến màn hình chi tiết và gửi dữ liệu sách
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("bookDetail", selectedBook);
                startActivity(intent);
            }
        });
    }

    public void onAddButtonClick(View view) {
        // Chuyển sang màn hình thêm sách
        Intent intent = new Intent(this, AddBook.class);
        startActivityForResult(intent, 1); // Sử dụng startActivityForResult nếu bạn cần nhận kết quả từ màn hình thêm sách
    }

    private void callApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.18:3000/")
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



    private void saveUserRole(String role) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userRole", role);
        editor.apply();
    }

    private void checkUserRole() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        userRole = preferences.getString("userRole", "defaultUserRole");
        Log.d("UserRole", "UserRole: " + userRole);
        if (!"admin".equals(userRole)) {
            addButton.setVisibility(View.GONE);
        }
    }

}
