package com.example.lab2.Bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2.Bai1.BackgroundTask_GET;
import com.example.lab2.R;

public class MainBai1 extends AppCompatActivity implements View.OnClickListener {
    TextView tvkq;
    String link;
    EditText edtName, edtScore;
    Button btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bai1);
        tvkq = findViewById(R.id.tvResult);
        edtName = findViewById(R.id.edtName);
        edtScore = findViewById(R.id.edtScore);
        btnLoad = findViewById(R.id.btnSend);
        link ="http://192.168.9.103/api_android/api_lab2.php" ;
        btnLoad.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        BackgroundTask_GET backgroundTaskGet = new BackgroundTask_GET(this,
                link,edtName.getText().toString(), edtScore.getText().toString(),tvkq);
        backgroundTaskGet.execute();
    }
}