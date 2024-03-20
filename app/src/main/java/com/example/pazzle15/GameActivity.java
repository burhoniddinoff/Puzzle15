package com.example.pazzle15;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SuppressLint("SetTextI18n")
public class GameActivity extends AppCompatActivity {
    private final Button[][] buttons = new Button[4][4];
    private final List<String> values = new ArrayList<>();
    private int x = 3;
    private int y = 3;
    static int count = 0;
    long time = 0;
    private static final int N = 4;
    private SharedPreferences pref;
    private Chronometer chronometer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        findViewById(R.id.refresh).setOnClickListener(v -> refresh());
        findViewById(R.id.back).setOnClickListener(v -> finish());

        chronometer = findViewById(R.id.chronometer);
        pref = this.getSharedPreferences("STATE", Context.MODE_PRIVATE);
        String date = pref.getString("MY_STATE", "!");

        count = pref.getInt("COUNT", 0);
        time = pref.getLong("TIME", 0);

        if (time != 0) chronometer.setBase(SystemClock.elapsedRealtime() + time);


        if (date.equals("!")) {
            refresh();
            return;
        }

        String[] s = date.split("#");

        Collections.addAll(values, s);


        TextView v = findViewById(R.id.count);
        v.setText("Count: " + count);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        for (int i = 0; i < 4; i++) {
            LinearLayout liner = (LinearLayout) linearLayout.getChildAt(i);
            for (int j = 0; j < 4; j++) {
                Button btn = (Button) liner.getChildAt(j);
                buttons[i][j] = btn;
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(this::onClick);
                btn.setTag(new Point(i, j));

            }


        }
        loadData();
        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

//        while (!isSolvable()) {
//            refresh();
//        }
    }


    @SuppressLint("CutPasteId")
    public void refresh() {
        ImageView image = findViewById(R.id.refresh);
        image.setClickable(false);
        count = 0;
        values.clear();
        initViews();
        initData();
        x = 3;
        y = 3;
        shuffle();
//        values.add("0");
        loadData();

        ImageView image2 = findViewById(R.id.refresh);
        image2.setOnClickListener(v -> refresh());
        image2.setClickable(true);

        while (!isSolvable()) refresh();

        chronometer = findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pref.edit().putLong("TIME", 0).apply();
        time = 0;
        chronometer.start();
    }

    private void loadData() {
        for (int i = 0; i < 16; i++) {
            if (values.get(i).equals("0")) {
                buttons[i / 4][i % 4].setVisibility(INVISIBLE);
                x = i / 4;
                y = i % 4;
            }

            buttons[i / 4][i % 4].setText(values.get(i));
        }
    }

    private void initViews() {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        TextView v = findViewById(R.id.count);
        v.setText("Count: " + count);

        for (int i = 0; i < 4; i++) {
            LinearLayout liner = (LinearLayout) linearLayout.getChildAt(i);

            for (int j = 0; j < 4; j++) {

                Button btn = (Button) liner.getChildAt(j);
                buttons[i][j] = btn;

                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(this::onClick);
                btn.setTag(new Point(i, j));

                if (i == 3 && j == 3) btn.setVisibility(INVISIBLE);

            }
        }

    }

    private void onClick(View v) {
        Button btn = (Button) v;
        Point currentPoint = (Point) btn.getTag();

        MediaPlayer mediaPlayer1 = MediaPlayer.create(this, R.raw.mp_4);
        mediaPlayer1.start();
        mediaPlayer1.release();
        mediaPlayer1.setOnCompletionListener(MediaPlayer::release);

        boolean canMove = (currentPoint.getX() == x && (Math.abs(currentPoint.getY() - y) == 1)) || (currentPoint.getY() == y && (Math.abs(currentPoint.getX() - x) == 1));

        if (canMove) {
            buttons[x][y].setText(btn.getText());
            buttons[x][y].setVisibility(VISIBLE);
            btn.setText("0");
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
        Log.d("TTTT", "win");

        for (int i = 0; i < 15; i++) {
            Log.d("TTT", buttons[i % 4][i / 4].getText().toString() + "//" + (i + 1));
            if (!buttons[i / 4][i % 4].getText().equals(String.valueOf(i + 1))) return;
        }

        MyShared myShared = MyShared.getInstance(this);
        myShared.saveMoveCount(count);

        chronometer.stop();


        MyDialog dialog = new MyDialog();
        dialog.setTime(count, chronometer.getBase());
        dialog.setCancelable(false);
        dialog.setSelectListener(new SelectListener() {

            @Override
            public void refreshButton() {
                refresh();
            }

            @Override
            public void menuButton() {
                refresh();
//                startActivity(new Intent(GameActivity.this, MainActivity.class));
                finish();
            }
        });
        dialog.show(getSupportFragmentManager(), "test");
    }


    private void shuffle() {
        Collections.shuffle(values);
        values.add("0");
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
    protected void onPause() {
        super.onPause();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            if (buttons[i / 4][i % 4].getText().equals("")) {
                sb.append("0").append("#");
            } else {
                sb.append(buttons[i / 4][i % 4].getText()).append("#");
            }
        }

        pref.edit().putString("Matrix", sb.toString()).apply();
        pref.edit().putInt("Score", count).apply();

        pref.edit().putLong("Time", chronometer.getBase() - SystemClock.elapsedRealtime()).apply();
        chronometer.stop();
    }

    @Override
    protected void onResume() {
        TextView score = findViewById(R.id.count);
        score.setText("Score : " + String.valueOf(pref.getInt("Score", 0)));

        count = pref.getInt("Score", 0);

        chronometer.setBase(pref.getLong("Time", 0) + SystemClock.elapsedRealtime());

        chronometer.start();

        String[] arr = pref.getString("Matrix", "").split("#");

        if (arr.length == 16) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("0")) {
                    buttons[i / 4][i % 4].setText("");
                    buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                    x = i / 4;
                    y = i % 4;
                } else {
                    buttons[i / 4][i % 4].setText(arr[i]);
                    buttons[i / 4][i % 4].setVisibility(View.VISIBLE);
                }
            }
        }

        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt("Count", count);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i)).append("#");
        }
        outState.putString("Values", sb.toString());

        outState.putLong("timer", chronometer.getBase());
        super.onSaveInstanceState(outState);
        chronometer.stop();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        count = savedInstanceState.getInt("Count");
        TextView textView = findViewById(R.id.count);
        textView.setText("Score : " + count);

        long timer = savedInstanceState.getLong("timer");
        chronometer.setBase(timer);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();

        StringBuilder sb = new StringBuilder();
        for (Button[] button : buttons) {
            for (int j = 0; j < buttons.length; j++) {
                sb.append(button[j].getText()).append("#");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        pref.edit().putString("STATE", sb.toString()).apply();
        pref.edit().putInt("COUNT", count).apply();
        pref.edit().putLong("TIME", chronometer.getBase() - SystemClock.elapsedRealtime()).apply();
    }



}