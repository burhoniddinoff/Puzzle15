package com.example.pazzle15;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WinActivity extends AppCompatActivity {

    private int count = 0;
    private SharedPreferences pref;
    private long time;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        pref = this.getSharedPreferences("STATE", Context.MODE_PRIVATE);
        count = getIntent().getIntExtra("COUNT", 0);
        time = getIntent().getLongExtra("TIME", 0);

//        TextView textView = findViewById(R.id.medal_1);
//        textView.setText(String.valueOf(count));

//        Chronometer chronometer = findViewById(R.id.time_1);
//        chronometer.setBase(time);


        findViewById(R.id.refresh_win).setOnClickListener(v -> {
            Intent intent = new Intent(WinActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
//        pref.edit().putInt("top1", top1).apply();
//        pref.edit().putInt("top2", top2).apply();
//        pref.edit().putInt("top3", top3).apply();
    }
}