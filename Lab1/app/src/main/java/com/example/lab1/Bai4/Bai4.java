package com.example.lab1.Bai4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab1.R;

public class Bai4 extends AppCompatActivity implements View.OnClickListener {
    private TextView tvResult;
    private Button btnRun;
    private EditText edtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        edtTime = findViewById(R.id.edtTime);
        btnRun = findViewById(R.id.btnRun);
        tvResult = findViewById(R.id.tvResult);
        btnRun.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRun:
                AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner(tvResult,edtTime,this);
                String sleepTime = edtTime.getText().toString();
                asyncTaskRunner.execute(sleepTime);
                break;
        }
    }
}