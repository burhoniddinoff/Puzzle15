package com.example.pazzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    private int count = 0;
    private int top1 = 0;
    private int top2 = 0;
    private int top3 = 0;
    private SharedPreferences pref;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        pref = this.getSharedPreferences("prefWin", Context.MODE_PRIVATE);
        count = getIntent().getIntExtra("COUNT", 0);
        top1 = pref.getInt("top1", 0);
        top2 = pref.getInt("top2", 0);
        top3 = pref.getInt("top3", 0);
        reyting();

        TextView medal1 = findViewById(R.id.medal_1);
        medal1.setText(String.valueOf(top1));

        TextView medal2 = findViewById(R.id.medal_2);
        medal2.setText(String.valueOf(top2));

        TextView medal3 = findViewById(R.id.medal_3);
        medal3.setText(String.valueOf(top3));

        findViewById(R.id.refresh_win).setOnClickListener(v -> {
            Intent intent = new Intent(WinActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void reyting() {
        if (top1 == 0 || top1 == count) {
            top1 = count;
        } else if (top1 > count) {
            top2 = top1;
            top1 = count;
        } else if (top2 == 0 || top2 == count) {
            top2 = count;
        } else if (top2 > count && top1 != count) {
            top3 = top2;
            top2 = count;
        } else if (top3 == 0 || top3 == count) {
            top3 = count;
        } else if (top3 > count && top2 != count) {
            top3 = count;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        pref.edit().putInt("top1", top1).apply();
        pref.edit().putInt("top2", top2).apply();
        pref.edit().putInt("top3", top3).apply();
    }
}