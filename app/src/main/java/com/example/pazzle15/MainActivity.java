package com.example.pazzle15;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button[][] buttons = new Button[4][4];
    private List<String> values = new ArrayList<>();
    private int x = 3;
    private int y = 3;
    static int count = 0;
    private static final int N = 4;
    private SharedPreferences preferences;
//    private MediaPlayer mediaPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mediaPlayer = MediaPlayer.create(this, R.raw.mp_3);
//        mediaPlayer.setVolume(0.2f, 0.2f);

        preferences = this.getSharedPreferences("CountPref", Context.MODE_PRIVATE);
        count = preferences.getInt("MY_COUNT", 0);


        View v = findViewById(R.id.refresh);
        v.setOnClickListener(v1 -> refresh());


        View v12 = findViewById(R.id.back);
        Toast.makeText(this, "" + v12.isClickable(), Toast.LENGTH_SHORT).show();
        v12.setOnClickListener(v239573125 -> {
            Toast.makeText(this, "next", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        });

        initViews();
        initData();
        shuffle();
//        values.add("");
        loadData();

        if (!isSolvable()) refresh();
    }


    @SuppressLint("CutPasteId")
    public void refresh() {
        ImageView image = findViewById(R.id.refresh);
        image.setClickable(false);
        count = 0;
        setContentView(R.layout.activity_main);
//        LinearLayout hide1 = findViewById(R.id.liner_layout);
//        hide1.setVisibility(View.INVISIBLE);
        values.clear();
        initViews();
        initData();
        x = 3;
        y = 3;
        shuffle();
        loadData();
        ImageView image2 = findViewById(R.id.refresh);
        image2.setOnClickListener(v -> refresh());
        while (!isSolvable()) refresh();

        count = 0;
        TextView k = findViewById(R.id.count);
        k.setText("Count: " + 0);
    }

    @SuppressLint("SetTextI18n")
    private void initViews() {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        TextView v = findViewById(R.id.count);
        v.setText("Count: " + count);

        for (int i = 0; i < relativeLayout.getChildCount(); i++) {
            int currentX = i / 4;
            int currentY = i % 4;

            Button currentBtn = (Button) relativeLayout.getChildAt(i);
            buttons[currentX][currentY] = currentBtn;
            currentBtn.setOnClickListener(this::onClick);
            currentBtn.setTag(new Point(currentX, currentY));

            if (currentX == 3 && currentY == 3) currentBtn.setVisibility(INVISIBLE);

        }
    }

    private void onClick(View v) {
        Button btn = (Button) v;
        Point currentPoint = (Point) btn.getTag();

        MediaPlayer mediaPlayer1 = MediaPlayer.create(this, R.raw.mp_4);
        mediaPlayer1.start();
//        mediaPlayer1.release();
//        mediaPlayer1.prepareAsync();
//
//        mediaPlayer1.setOnErrorListener((mp, what, extra) -> {
//             Handle errors here
//            return false;
//        });

        boolean canMove = (currentPoint.getX() == x && (Math.abs(currentPoint.getY() - y) == 1)) || (currentPoint.getY() == y && (Math.abs(currentPoint.getX() - x) == 1));

        if (canMove) {
            buttons[x][y].setText(btn.getText());
            buttons[x][y].setVisibility(VISIBLE);
            btn.setText("");
            btn.setVisibility(INVISIBLE);
            x = currentPoint.getX();
            y = currentPoint.getY();

            count++;
            TextView k = findViewById(R.id.count);
            k.setText("Count: " + count);


            if (x == 3 && y == 3) checkWin();
        }
    }


    private void initData() {
        for (int i = 1; i < 16; i++) {
            values.add(String.valueOf(i));
        }
    }

    private void checkWin() {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);

        for (int i = 1; i < 16; i++) {
            Button btn = (Button) relativeLayout.getChildAt(i - 1);

            if (!btn.getText().equals(String.valueOf(i))) return;
        }

//        findViewById(R.id.refresh).setVisibility(INVISIBLE);
//        findViewById(R.id.refresh2).setVisibility(VISIBLE);

//        @SuppressLint("WrongViewCast") LinearLayout layout = findViewById(R.id.liner_layout);
//        layout.setVisibility(VISIBLE);

//        findViewById(R.id.refresh2).setOnClickListener(v1 -> {
//            refresh();
////            layout.setVisibility(INVISIBLE);
//            findViewById(R.id.refresh2).setVisibility(INVISIBLE);
//        });


        Intent intent = new Intent(MainActivity.this, WinActivity.class);
        intent.putExtra("COUNT", count);
        count = 0;
        startActivity(intent);
        finish();

    }

    private void loadData() {
        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4].setText(values.get(i));
        }
    }


    private void shuffle() {
        Collections.shuffle(values);
        values.add("");
    }

    private int getInvCount(int[] arr) {
        int inv_count = 0;
        for (int i = 0; i < N * N - 1; i++) {
            for (int j = i + 1; j < N * N; j++) {
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j]) inv_count++;
            }
        }
        return inv_count;
    }

    private boolean isSolvable() {
        int[] arr;
        arr = convertTo1DArray();
        int invCount = getInvCount(arr);

        if (N % 2 == 1) return invCount % 2 == 0;
        else {
            int pos = x;
            if (pos % 2 == 1) return invCount % 2 == 0;
            else return invCount % 2 == 1;
        }
    }

    private int[] convertTo1DArray() {
        int[] arr = new int[N * N];
        int k = 0;
        for (int i = 0; i < N * N; i++) {
            if (values.get(i).equals("")) {
                arr[k++] = 0;
                continue;
            }
            arr[k++] = Integer.parseInt(values.get(i));
        }
        return arr;
    }


    @Override
    protected void onResume() {
        super.onResume();
//        mediaPlayer.start();
//        mediaPlayer.setLooping(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mediaPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        preferences.edit().putInt("MY_COUNT", count).apply();
//        mediaPlayer.getCurrentPosition();
    }
}