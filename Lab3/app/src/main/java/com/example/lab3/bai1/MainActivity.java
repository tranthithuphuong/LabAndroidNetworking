package com.example.lab3.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.lab3.R;
import com.example.lab3.bai1.GetContact;


public class MainActivity extends AppCompatActivity {
    private ListView lvContact;
    GetContact getContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContact = findViewById(R.id.listContacst);

        getContact = new GetContact(lvContact,this);
        getContact.execute();


    }
}