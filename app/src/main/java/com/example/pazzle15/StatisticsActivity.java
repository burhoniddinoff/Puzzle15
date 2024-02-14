package com.example.pazzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {
    private int top1 = 0;
    private int top2 = 0;
    private int top3 = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        MyShared myShared = MyShared.getInstance(this);
        int[] arr = myShared.getResults();
        boolean b = true;

        if (b) {
            if (arr[0] != Integer.MAX_VALUE) {
                top1 = arr[0];
            }
            if (arr[1] != Integer.MAX_VALUE) {
                top2 = arr[1];
            }
            if (arr[2] != Integer.MAX_VALUE) {
                top3 = arr[2];
            }
        }

        ((TextView) findViewById(R.id.static_1)).setText(String.valueOf(top1));
        ((TextView) findViewById(R.id.static_2)).setText(String.valueOf(top2));
        ((TextView) findViewById(R.id.static_3)).setText(String.valueOf(top3));

        findViewById(R.id.menu_static).setOnClickListener(v -> finish());
        findViewById(R.id.game_static).setOnClickListener(v -> {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });

    }
}