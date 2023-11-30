package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asm.api.api;
import com.example.asm.model.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditBookActivity extends AppCompatActivity {
    private EditText editTextBookName;
    private EditText editTextBookDirection;
    private EditText editTextBookImageLink;
    private Button buttonUpdateBook;
    private Book bookToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        editTextBookName = findViewById(R.id.editTextBookName);
//        editTextBookDirection = findViewById(R.id.editTextBookDirection);
        editTextBookImageLink = findViewById(R.id.editTextBookImageLink);
        buttonUpdateBook = findViewById(R.id.buttonUpdateBook);

        // Nhận dữ liệu sách cần chỉnh sửa từ Intent
        bookToEdit = getIntent().getParcelableExtra("bookToEdit");
        Log.e("API", "Book To Edit: " + bookToEdit.getId());
            // Hiển thị thông tin của cuốn sách cần chỉnh sửa trong các EditText
            editTextBookName.setText(bookToEdit.getName());
//            editTextBookDirection.setText(bookToEdit.getDirection());
            editTextBookImageLink.setText(bookToEdit.getAvatar());

        buttonUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateBookClick();
            }
        });
    }

    private void onUpdateBookClick() {
        // Lấy thông tin sách đã chỉnh sửa từ EditText
        String updatedBookName = editTextBookName.getText().toString().trim();
//        String updatedBookDirection = editTextBookDirection.getText().toString().trim();
        String updatedBookImageLink = editTextBookImageLink.getText().toString().trim();

        // Kiểm tra xem các trường có rỗng không
        if (!updatedBookName.isEmpty() && !updatedBookImageLink.isEmpty()) {
            // Cập nhật thông tin sách và gọi API để lưu thay đổi
            bookToEdit.setName(updatedBookName);
//            bookToEdit.setDirection(updatedBookDirection);
            bookToEdit.setAvatar(updatedBookImageLink);

            // Gọi API để cập nhật sách
            updateBookApi(bookToEdit);
        } else {
            // Hiển thị thông báo hoặc xử lý khi có trường rỗng
        }
    }

    private void updateBookApi(Book updatedBook) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.18:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api apiService = retrofit.create(api.class);

        Call<Void> call = apiService.updateBook(String.valueOf(updatedBook.getId()), updatedBook);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent mainIntent = new Intent(EditBookActivity.this, MainActivity.class);
                    mainIntent.putExtra("userRole", "admin");
                    startActivity(mainIntent);
                    finish();
                } else {
                    Log.e("API", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }
}
