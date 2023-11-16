package com.example.asm;// AddBookActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm.R;
import com.example.asm.api.api;
import com.example.asm.model.Book;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBook extends AppCompatActivity {

    private EditText editTextBookName;
    private EditText editTextBookDirection;
    private EditText editTextBookImageLink;
    private Button buttonAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextBookName = findViewById(R.id.editTextBookName);
        editTextBookDirection = findViewById(R.id.editTextBookDirection);
        editTextBookImageLink = findViewById(R.id.editTextBookImageLink);
        buttonAddBook = findViewById(R.id.buttonAddBook);

        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddBookClick();
            }
        });
    }

    private void onAddBookClick() {
        String bookName = editTextBookName.getText().toString().trim();
        String bookDirection = editTextBookDirection.getText().toString().trim();
        String bookImageLink = editTextBookImageLink.getText().toString().trim();

        // Kiểm tra xem các trường có rỗng không
        if (!bookName.isEmpty() && !bookDirection.isEmpty() && !bookImageLink.isEmpty()) {
            // Tạo đối tượng Book từ dữ liệu nhập
            Book newBook = new Book();
            newBook.setName(bookName);
            newBook.setDirection(bookDirection);
            newBook.setAvatar(bookImageLink);

            // Gọi API để thêm sách
            addBookToApi(newBook);
        } else {
            // Hiển thị thông báo hoặc xử lý khi có trường rỗng
        }
    }

    private void addBookToApi(Book newBook) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://632c7f491aabd837399d7c73.mockapi.io/") // Đường dẫn cơ sở của API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api apiService = retrofit.create(api.class);

        Call<Book> call = apiService.addBook(newBook);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()) {

                    Intent mainIntent = new Intent(AddBook.this, MainActivity.class);
                    startActivity(mainIntent);

                    // Đóng màn hình thêm sách sau khi thêm xong
                    finish();
                } else {
                    // Xử lý khi có lỗi từ API
                    // Hiển thị thông báo hoặc xử lý tùy thuộc vào yêu cầu của bạn
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                // Xử lý khi có lỗi kết nối hoặc lỗi khác
                // Hiển thị thông báo hoặc xử lý tùy thuộc vào yêu cầu của bạn
            }
        });
    }
}
