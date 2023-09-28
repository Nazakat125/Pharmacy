package com.example.projectp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class suplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplash);

        ImageView imageView = findViewById(R.id.splash_screen_image);
        Glide.with(this)
                .asGif()
                .load(R.drawable.supplash)
                .into(imageView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(suplash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}