package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Bai2 extends AppCompatActivity {
    private  static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Bai2.this, Bai1.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}