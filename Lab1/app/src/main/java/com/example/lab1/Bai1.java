package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class Bai1 extends AppCompatActivity implements View.OnClickListener {

    private  Button btnLoad;
    TextView tvMes;
    ImageView imgAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        btnLoad = findViewById(R.id.btnLoad);
        tvMes = findViewById(R.id.tvMes);
        imgAndroid = findViewById(R.id.imgAndroid);
        btnLoad.setOnClickListener(this);
        
    }

    private Bitmap LoadImageFromNetWord(String link){
        URL url;
        Bitmap bitmap = null;
        try {
            url = new URL(link);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
        return  bitmap;
    }

    @Override
    public void onClick(View v) {
        final  Thread thread= new Thread(new Runnable() {
            @Override
            public void run() {
                final  Bitmap bitmap = LoadImageFromNetWord("https://ap.poly.edu.vn/images/logo.png");
                imgAndroid.post(new Runnable() {
                    @Override
                    public void run() {
                        tvMes.setText("Image DownLoaded");
                        imgAndroid.setImageBitmap(bitmap);
                        btnLoad.setText("DownLoad Thành Công");
                    }
                });
            }
        });
        thread.start();
    }
}