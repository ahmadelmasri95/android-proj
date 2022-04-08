package com.example.personaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView joke_text;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        joke_text = this.findViewById(R.id.joke_text);
        button = this.findViewById(R.id.button);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}