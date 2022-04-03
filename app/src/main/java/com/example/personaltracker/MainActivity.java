package com.example.personaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AppOpsManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

//This activity retrieves the app usage from instagram and whatsapp

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize views
        floatingActionButton = this.findViewById(R.id.floatingActionButton);
        textView1 = this.findViewById(R.id.textView1);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the app usage time
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}