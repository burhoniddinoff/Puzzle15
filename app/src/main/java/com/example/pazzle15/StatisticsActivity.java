package com.example.pazzle15;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity {
    private int top1 = 0;
    private int top2 = 0;
    private int top3 = 0;


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
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

        if (((TextView) findViewById(R.id.static_1)).getText().equals("0")) {
            ((TextView) findViewById(R.id.static_1)).setText("No");
        }

        if (((TextView) findViewById(R.id.static_2)).getText().equals("0")) {
            ((TextView) findViewById(R.id.static_2)).setText("No");
        }

        if (((TextView) findViewById(R.id.static_3)).getText().equals("0")) {
            ((TextView) findViewById(R.id.static_3)).setText("No");
        }

    }
}