package com.example.pazzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) openInfoScreen();
//        else {
            Handler handler = new Handler(getMainLooper());
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
//        }
    }

    private void openInfoScreen() {
        Intent intent = new Intent(SplashActivity.this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}