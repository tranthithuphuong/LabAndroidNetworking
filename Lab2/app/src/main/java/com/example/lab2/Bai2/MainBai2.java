package com.example.lab2.Bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2.R;

public class MainBai2 extends AppCompatActivity implements View.OnClickListener {
    TextView tvkq;
    EditText edtDai, edtRong;
    Button btnLoad, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai2);

        tvkq = findViewById(R.id.tvResult);
        edtDai = findViewById(R.id.edtDai);
        edtRong = findViewById(R.id.edtRong);
        btnLoad = findViewById(R.id.btnSend2);

        btnLoad.setOnClickListener(this);
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
        String link ="http://10.24.21.142/api_android/api_lab2_bai2.php";
        AsyncTask_POST taskPost = new AsyncTask_POST(this, edtDai.getText().toString(), edtRong.getText().toString(),link,tvkq);
        taskPost.execute();
    }
}