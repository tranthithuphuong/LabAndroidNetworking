package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.lab2.Bai1.MainBai1;
import com.example.lab2.Bai2.MainBai2;
import com.example.lab2.Bai3.MainBai3;
import com.example.lab2.bai4.bai4;


public class MainActivity extends AppCompatActivity {

    private Button btnB1;
    private Button btnB2;
    private Button btnB3;
    private Button btnB4;
    private Class hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnB1 = (Button) findViewById(R.id.btn_b1);
        btnB2 = (Button) findViewById(R.id.btn_b2);
        btnB3 = (Button) findViewById(R.id.btn_b3);
        btnB4 = (Button) findViewById(R.id.btn_b4);

        btnB1.setOnClickListener(v -> executeExercise(1));
        btnB2.setOnClickListener(v -> executeExercise(2));
        btnB3.setOnClickListener(v -> executeExercise(3));
        btnB4.setOnClickListener(v -> executeExercise(4));
    }

    private void executeExercise(int exerciseNumber) {
        switch (exerciseNumber) {
            case 1:
                hm = MainBai1.class;
                break;

            case 2:
                hm = MainBai2.class;
                break;

            case 3:
                hm = MainBai3.class;
                break;

            case 4:
                hm = bai4.class;

        }

        startActivity(new Intent(MainActivity.this, hm));
    }
}