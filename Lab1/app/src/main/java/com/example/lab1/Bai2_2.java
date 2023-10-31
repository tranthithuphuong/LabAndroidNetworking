package com.example.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Bai2_2 extends AppCompatActivity implements View.OnClickListener {
    private ImageView  imgLoad;
    private Button btnLoad1;
    private ProgressDialog progressDialog;
    private String url = "https://ap.poly.edu.vn/images/logo.png";
    private TextView tvMes;
    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai22);
        imgLoad = findViewById(R.id.imgLoad);
        btnLoad1 = findViewById(R.id.btnLoad1);
        tvMes = findViewById(R.id.tvMes);
        btnLoad1.setOnClickListener(this);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String message = bundle.getString("message");
            tvMes.setText(message);
            imgLoad.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        progressDialog = ProgressDialog.show(Bai2_2.this,"","DownLoading....");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bitmap  = downloadbitmap(url);
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                String threadMes = "Image download";
                bundle.putString("message", threadMes);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Bitmap downloadbitmap(String link) {
        try {
            URL url1 = new URL(link);
            HttpURLConnection  connection = (HttpURLConnection) url1.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
            return bitmap1;
        } catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}