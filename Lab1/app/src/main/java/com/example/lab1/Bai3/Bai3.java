package com.example.lab1.Bai3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lab1.R;


public class Bai3 extends AppCompatActivity implements View.OnClickListener, Listener {
    private TextView tvMes3;
    private Button btnLoad3;
    private ImageView imgLoad3;
    public static final String IMAGE_URL ="https://ap.poly.edu.vn/images/logo.png";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        tvMes3 = findViewById(R.id.tvMes3);
        btnLoad3 = findViewById(R.id.btnLoad3);
        imgLoad3 = findViewById(R.id.imgLoad3);
        btnLoad3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLoad3:
                new LoadImageTask(this,this).execute(IMAGE_URL);
            break;
        }
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        imgLoad3.setImageBitmap(bitmap);
        tvMes3.setText("Image downloaded");
    }

    @Override
    public void onError() {
        tvMes3.setText("Error download image");
    }
}