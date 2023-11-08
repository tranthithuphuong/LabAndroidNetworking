package com.example.lab2.bai4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lab2.R;


public class bai4 extends Activity implements View.OnClickListener {
    public static final String SERVER_NAME ="http://10.24.21.142/api_android/api_lab2_bai4.php";
private EditText edsoa,edsob,edsoc;
private Button btnSend, btnBack;
private TextView tvketqua;
String strSoa , strSob , strSoc;
BackgroundTask_POST bpost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        edsoa = findViewById(R.id.edsoa);
        edsob = findViewById(R.id.edsob);
        edsoc = findViewById(R.id.edsoc);
        tvketqua = findViewById(R.id.tvkq);
        btnSend = findViewById(R.id.btnsend);
        btnSend.setOnClickListener(this);

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

        if (v.getId() == R.id.btnsend) {
            strSoa = edsoa.getText().toString();
            strSob = edsob.getText().toString();
            strSoc = edsoc.getText().toString();
            bpost = new BackgroundTask_POST(this, tvketqua);
            bpost.execute(strSoa, strSob, strSoc);
        } else {

        }
    }
}