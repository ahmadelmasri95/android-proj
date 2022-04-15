package com.example.personaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.personaltracker.api.ApiUtilities;
import com.example.personaltracker.api.Plan;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* This app mainly gets data from BoredAPI, then according
to the random information, checks for nearby activities on a map
and displays to user after searching
* */
public class MainActivity extends AppCompatActivity {

    //for progressBar alert
    private ProgressBar progressBar;
    private Handler alertHandler;

    //retrieved data from boredApi
    private List<Plan> plans;

    //for the animated button
    private AppCompatButton button;
    private Handler animationHandler;
    private boolean AnimationStatus = false;
    private ImageView imageAnimation;
    private ImageView imageAnimation2;

    //check if can use one handler for all the runnables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize MainActivity views
        init();
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

    }

    public void init() {
        progressBar = this.findViewById(R.id.progressBar);
        button = this.findViewById(R.id.searchButton);
        imageAnimation = this.findViewById(R.id.img_animation);
        imageAnimation2 = this.findViewById(R.id.img_animation2);
        plans = new ArrayList<>();
        animationHandler = new Handler(this.getMainLooper());
        alertHandler = new Handler(this.getMainLooper());

    }

    public void startPulse() {
        animationHandler.post(animationRunnable);
        alertHandler.postDelayed(progressRunnable, 8000);
        getAndClusterData();
        //if we find a match on the plan with the map, change color of progressbar
//        progressBar.getIndeterminateDrawable().setColorFilter(
//                getResources().getColor(R.color.green),
//                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public void stopPulse() {
        animationHandler.removeCallbacks(animationRunnable);
        alertHandler.removeCallbacks(progressRunnable);
        progressBar.setIndeterminate(false);
        Toast.makeText(this, String.valueOf(plans.size()), Toast.LENGTH_LONG).show();
    }

    public void getAndClusterData() {
        //Asynchronous rest api call - GET
        //Group plans by type in a hashMap<String i.e. "type", List<String> i.e. "activities">
        for (int i = 0; i < 15; i++) {
            ApiUtilities.getApiInterface().getPlan("activity")
                    .enqueue(new Callback<Plan>() {
                        @Override
                        public void onResponse(@NonNull Call<Plan> call, @NonNull Response<Plan> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    plans.add(response.body());
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
    }

    private final Runnable animationRunnable = new Runnable() {
        //Animates the main search button
        @Override
        public void run() {
            imageAnimation.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(900)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            imageAnimation.setScaleX(1f);
                            imageAnimation.setScaleY(1f);
                            imageAnimation.setAlpha(1f);
                        }
                    });
            imageAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(600)
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

    private final Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setIndeterminate(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}