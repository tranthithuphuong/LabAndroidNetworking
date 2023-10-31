package com.example.lab_androidnetetworking.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lab_androidnetetworking.model.PhotoModel;
import com.example.lab_androidnetetworking.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<PhotoModel> photos;
    private Context context;

    public PhotoAdapter(List<PhotoModel> photos, Context context) {
        this.photos = photos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhotoModel photo = photos.get(position);
        holder.photoTextView.setText(photo.getTitle());
        holder.idTextView.setText(String.valueOf(photo.getId()));
        Picasso.get().load(photo.getUrl()).into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView photoTextView;

        TextView idTextView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            photoTextView = itemView.findViewById(R.id.photoTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
        }
    }
}
