package com.example.pazzle15;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class MyDialog extends DialogFragment {
    private SelectListener listener;
    private int count = 0;
    private SharedPreferences pref;
    private long time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_win, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        TextView textView = view.findViewById(R.id.medal_1);
        view.findViewById(R.id.refresh_win).setOnClickListener(v -> {
            listener.refreshButton();
            dismiss();
        });

        view.findViewById(R.id.menu_1234).setOnClickListener(v -> {
            listener.menuButton();
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(Objects.requireNonNull(this.getDialog()).getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public void setSelectListener(SelectListener listener) {
        this.listener = listener;
    }
}
