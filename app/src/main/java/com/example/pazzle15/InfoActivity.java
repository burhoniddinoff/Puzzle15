package com.example.pazzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class InfoActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

//        findViewById(R.id.telegram).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/brxnw"))));
//        findViewById(R.id.gita).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/gitauz"))));
//        findViewById(R.id.github).setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/burhoniddinoff"))));

        findViewById(R.id.menu).setOnClickListener(v -> finish());

        findViewById(R.id.game).setOnClickListener(v -> {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });


    }
}