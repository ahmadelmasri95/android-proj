package com.example.personaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//This activity retrieves the app usage from instagram and whatsapp

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton = this.findViewById(R.id.floatingActionButton);
        textView1 = this.findViewById(R.id.textView1);
        Toast.makeText(this, "activity started", Toast.LENGTH_SHORT).show();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the app usage time
               startService(new Intent(getApplication(),AppUsageService.class));
            }
        });
    }
    public void getAppUsage(){

    }

}