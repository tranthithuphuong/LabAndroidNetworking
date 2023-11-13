package com.example.lab3.Bai3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;

    public DataAdapter(ArrayList<AndroidVersion> android) {
        this.android = android;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AndroidVersion currentAndroid = android.get(position);
        holder.tv_name.setText(currentAndroid.getName());
        holder.tv_version.setText(currentAndroid.getVer());
        holder.tv_api_level.setText(currentAndroid.getApi());
    }

    @Override
    public int getItemCount() {
        return android.size(); // Return the size of the dataset
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_version, tv_api_level;

        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_version = view.findViewById(R.id.tv_version);
            tv_api_level = view.findViewById(R.id.tv_api_level);
        }
    }
}

