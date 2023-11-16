package com.example.asm.api;

import com.example.asm.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ProductApi {
    @GET("products")
    Call<List<Product>> getProducts();

    @POST("products")
    Call<Product> addProduct(@Body Product product);

    @PUT("products/{id}")
    Call<Product> updateProduct(@Path("id") String productId, @Body Product product);

    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") String productId);
}
