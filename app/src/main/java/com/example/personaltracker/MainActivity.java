package com.example.personaltracker;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.personaltracker.api.ApiUtilities;
import com.example.personaltracker.api.Plan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* This app mainly gets data from BoredAPI, then according
to the random information, checks for nearby activities on a map
and displays to user after searching
* */
public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;

    //retrieved data from boredApi
    private Plan plan;

    //for the animated button
    private AppCompatButton button;
    private Handler animationHandler;
    private boolean AnimationStatus = false;
    private ImageView imageAnimation;
    private ImageView imageAnimation2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize MainActivity views
        init();

        //progressBar idle at first
        progressBar.setIndeterminate(false);


        //Create searchButton pulse animations and change state of selector
        button.setOnClickListener(v -> {
            if (AnimationStatus) {
                stopPulse();
                button.setText("Search");
            } else {
                startPulse();
                button.setText("Stop");
            }
            AnimationStatus = !AnimationStatus;
        });

        //Go to next Activity, & other processing
        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        //Asynchronous call rest api call
        ApiUtilities.getApiInterface().getPlan("activity")
                .enqueue(new Callback<Plan>() {
                    @Override
                    public void onResponse(@NonNull Call<Plan> call, @NonNull Response<Plan> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                plan = (response.body());
                            }
                        } else {
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Plan> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void init() {
        floatingActionButton = this.findViewById(R.id.fab);
        progressBar = this.findViewById(R.id.progressBar);
        button = this.findViewById(R.id.searchButton);
        imageAnimation = this.findViewById(R.id.img_animation);
        imageAnimation2 = this.findViewById(R.id.img_animation2);
        plan = new Plan();
        animationHandler = new Handler(this.getMainLooper());

    }

    public void startPulse() {
        animationHandler.post(runnable);
        progressBar.setIndeterminate(true);

        //if we find a match on the plan with the map, change color of progressbar
//        progressBar.getIndeterminateDrawable().setColorFilter(
//                getResources().getColor(R.color.green),
//                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void stopPulse() {
        animationHandler.removeCallbacks(runnable);
        progressBar.setIndeterminate(false);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageAnimation.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(700)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imageAnimation.setScaleX(1f);
                            imageAnimation.setScaleY(1f);
                            imageAnimation.setAlpha(1f);
                        }
                    });
            imageAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(400)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imageAnimation.setScaleX(1f);
                            imageAnimation.setScaleY(1f);
                            imageAnimation.setAlpha(1f);
                        }
                    });
            animationHandler.postDelayed(this, 1500);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}