package com.example.lab2.Bai3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2.R;

public class MainBai3 extends AppCompatActivity implements View.OnClickListener {
    TextView tvkq;
    EditText edtCanh;
    Button btnGui, btnBack;
    String strCanh;

    BackgroundTask_POST_bai3 postBai3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai3);

        tvkq = findViewById(R.id.tvkq3);
        edtCanh = findViewById(R.id.edtCanh);
        btnGui = findViewById(R.id.btnGui);
        btnGui.setOnClickListener(this);

        btnBack = findViewById(R.id.btnBack); // Gán nút "Back" từ layout
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nút "Back" được nhấn
                onBackPressed(); // Quay trở lại màn hình chính (MainActivity)
            }
        });
    }

    @Override
    public void onClick(View v) {
        strCanh = edtCanh.getText().toString();
        postBai3 = new BackgroundTask_POST_bai3(this, tvkq);
        postBai3.execute(strCanh);
    }
}