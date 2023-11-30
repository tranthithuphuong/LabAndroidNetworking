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
    private String userRole;
    private OnItemClickListener listener;
    public BookAdapter(Context context, List<Book> bookList, String userRole) {
        this.context = context;
        this.bookList = bookList;
        this.userRole = userRole;

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

        // Ensure that the imageViewAvatar field is properly defined as an ImageView
        Picasso.get()
                .load(book.getAvatar())
                .into(holder.imageViewAvatar);

        if (!"admin".equals(userRole)) {
            holder.buttonDelete.setVisibility(View.GONE);
            holder.buttonEdit.setVisibility(View.GONE);
        } else {
            // Nếu là admin, hiển thị nút "Sửa" và "Xoá"
            holder.buttonDelete.setVisibility(View.VISIBLE);
            holder.buttonEdit.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Phương thức để thiết lập sự kiện lắng nghe
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public Book getItem(int position) {
        return bookList.get(position);
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

    public void setUserRole(String userRole) {
        this.userRole = userRole;
        notifyDataSetChanged(); // Cập nhật adapter khi có giá trị mới
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewAvatar; // Ensure this is an ImageView
        TextView textViewName;
        Button buttonDelete;
        Button buttonEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
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
                    .baseUrl("http://192.168.1.18:3000/")
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
