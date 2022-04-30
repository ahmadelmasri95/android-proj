package com.example.personaltracker;

import static retrofit2.Response.error;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.personaltracker.api.ApiUtilities;
import com.google.android.material.textview.MaterialTextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* This app :
-
 */

public class MainActivity extends AppCompatActivity {

    //for progressBar alert
    private ProgressBar progressBar;
    private Handler alertHandler;

    //for the animated button
    private AppCompatButton button;
    private Handler animationHandler;
    private boolean AnimationStatus = false;
    private ImageView imageAnimation;
    private ImageView imageAnimation2;
    private ImageView imageView;
    private MaterialTextView textView;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    //check if can use one handler for all the runnable

    private final Runnable animationRunnable = new Runnable() {
        //Animates the main search button
        @Override
        public void run() {
            imageAnimation.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(900)
                    .withEndAction(() -> {
                        imageAnimation.setScaleX(1f);
                        imageAnimation.setScaleY(1f);
                        imageAnimation.setAlpha(1f);
                    });
            imageAnimation2.animate().scaleX(4f).scaleY(4f).alpha(0f).setDuration(600)
                    .withEndAction(() -> {
                        imageAnimation.setScaleX(1f);
                        imageAnimation.setScaleY(1f);
                        imageAnimation.setAlpha(1f);
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

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double latitude = intent.getDoubleExtra("lati", 0);
            double longitude = intent.getDoubleExtra("longi", 0);
            textView.setText(latitude + " " + longitude);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize MainActivity views
        init();
        //Create searchButton pulse animations and change state of selector
        button.setOnClickListener(v -> {
            if (AnimationStatus) {
                stopLocationService();
                stopPulse();
                button.setText(R.string.button_initial_state);
            } else {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION);
                } else {
                    startLocationService();
                }
                startPulse();
                button.setText(R.string.button_secondary_state);
            }
            AnimationStatus = !AnimationStatus;
        });
    }

    @Override
    protected void onStart() { //can use this to start getting the location instead of a button press
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationService.TRANSFER_DATA_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    private void init() {
        progressBar = this.findViewById(R.id.progressBar);
        button = this.findViewById(R.id.searchButton);
        imageAnimation = this.findViewById(R.id.img_animation);
        imageAnimation2 = this.findViewById(R.id.img_animation2);
        animationHandler = new Handler(this.getMainLooper());
        alertHandler = new Handler(this.getMainLooper());
        imageView = this.findViewById(R.id.bored1);
        textView = this.findViewById(R.id.coordinates);
    }

    private void startPulse() {
        animationHandler.post(animationRunnable);
        alertHandler.postDelayed(progressRunnable, 8000);

        //  getData();

//        RequaestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);
//        Glide.with(this)
//                .load("https://image.thum.io/get/auth/57066-Masri/png/" + textInputEditText.getText().toString())
//                .into(imageView);


        //to change color of progressbar
//        progressBar.getIndeterminateDrawable().setColorFilter(
//                getResources().getColor(R.color.green),
//                android.graphics.PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void stopPulse() {
        animationHandler.removeCallbacks(animationRunnable);
        alertHandler.removeCallbacks(progressRunnable);
        progressBar.setIndeterminate(false);
    }

    private void startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service initiated.", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService() {
        if (isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped.", Toast.LENGTH_SHORT).show();
        }
    }

    public void getData() {
        //Asynchronous rest api call - GET
        ApiUtilities.getApiInterface().getScreenshot("https://www.nba.com")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                            }

                        } else {
                            Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}