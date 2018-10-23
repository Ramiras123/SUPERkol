package com.example.ramiras.myapplication;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.print.PrintAttributes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TestFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonClick = findViewById(R.id.button);
        fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        buttonClick.setOnClickListener(v -> {
            fragment.clear();
        });
    }
}
