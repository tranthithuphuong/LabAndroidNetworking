package com.example.asm.api;

import com.example.asm.model.Book;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface api {
    @GET("books") // Thay thế bằng endpoint thực tế của API để lấy danh sách sách
    Call<List<Book>> getBooks();

    @POST("books") // Đường dẫn cụ thể của API để thêm sách
    Call<Book> addBook(@Body Book book);

    @DELETE("books/{id}")
    Call<Void> deleteBook(@Path("id") String bookId);

    @PUT("books/{id}") // Đường dẫn cụ thể của API để chỉnh sửa sách
    Call<Void> updateBook(@Path("id") String bookId, @Body Book updatedBook);
}
