package com.example.lab3.bai4.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.R;
import com.example.lab3.bai4.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Contact> contacts;
    private Context context;

    public PhotoAdapter(List<Contact> contacts, Context context) {
        this.contacts = contacts;
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
        Contact photo = contacts.get(position);
        holder.photoTextView.setText(photo.getEmail());
        holder.idTextView.setText(String.valueOf(photo.getName()));
        Picasso.get().load(photo.getProfilePic()).into(holder.photoImageView);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
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
