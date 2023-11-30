package com.example.asm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asm.model.Book;
import com.squareup.picasso.Picasso;

public class Detail extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private TextView textViewName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewAvatar = findViewById(R.id.imageViewAvatarDetail);
        textViewName = findViewById(R.id.textViewNameDetail);
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("bookDetail")) {
            Book book = intent.getParcelableExtra("bookDetail");
            if (book != null) {
                Picasso.get().load(book.getAvatar()).into(imageViewAvatar);
                textViewName.setText(book.getName());
            }
        }
    }
}