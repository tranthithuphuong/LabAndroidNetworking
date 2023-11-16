package com.example.asm.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.EditBookActivity;
import com.example.asm.R;
import com.example.asm.api.api;
import com.example.asm.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList;
    private Context context;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.textViewName.setText(book.getName());
        holder.textViewDirection.setText(book.getDirection());

        // Ensure that the imageViewAvatar field is properly defined as an ImageView
        Picasso.get()
                .load(book.getAvatar())
                .into(holder.imageViewAvatar);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setData(List<Book> newData) {
        bookList.clear();
        bookList.addAll(newData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar; // Ensure this is an ImageView
        TextView textViewName;
        TextView textViewDirection;
        Button buttonDelete;
        Button buttonEdit;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDirection = itemView.findViewById(R.id.textViewDirection);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gọi phương thức để xử lý sự kiện xóa
                    onDeleteClick(getAdapterPosition());
                }
            });

            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Gọi phương thức để xử lý sự kiện sửa
                    onEditClick(getAdapterPosition());
                }
            });

        }
        private void onDeleteClick(int position) {
            // Lấy cuốn sách tại vị trí được chọn
            Book bookToDelete = bookList.get(position);

            String bookId = String.valueOf(bookToDelete.getId());

            // Gọi phương thức để xóa cuốn sách và cập nhật RecyclerView
            deleteBookApi(bookId, position);
        }
        private void deleteBookApi(String bookId, final int position) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://632c7f491aabd837399d7c73.mockapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            api apiService = retrofit.create(api.class);

            Call<Void> call = apiService.deleteBook(bookId);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Xóa cuốn sách từ danh sách và thông báo RecyclerView
                        bookList.remove(position);
                        notifyItemRemoved(position);
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
        private void onEditClick(int position) {
            // Lấy cuốn sách tại vị trí được chọn
            Book bookToEdit = bookList.get(position);
            Log.e("API", "Book List Size: " + bookList.size());
            Log.e("API", "Book To Edit: " + bookToEdit.getId());

            // Chuyển sang màn hình chỉnh sửa và gửi dữ liệu sách cần sửa
            Intent intent = new Intent(context, EditBookActivity.class);
            intent.putExtra("bookToEdit", bookToEdit);
            context.startActivity(intent);
        }
    }
}
