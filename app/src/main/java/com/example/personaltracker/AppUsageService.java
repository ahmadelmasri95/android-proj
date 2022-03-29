package com.example.personaltracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.widget.Toast;

import androidx.annotation.Nullable;

//background service that gets the apps usage continuously
public class AppUsageService extends Service {

    public Handler handler = null;
    public Context context = this;
    public static Runnable runnable = null;
    public Toast toast = null;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler(Looper.getMainLooper());
        toast = new Toast(context);
        runnable = new Runnable() {
            @Override
            public void run() {
                toast.makeText(context, "service still running", Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable, 3000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toast.makeText(context, "service started by user", Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable, 3000);
        return START_STICKY; //restarts service when phone memory gets recovered from low
    }

    @Override
    public void onDestroy() {
        //onDestroy() will get called when the application gets closed
        // or killed but the runnable just starts it right back up
        toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
        handler.removeCallbacks(runnable);
        toast.cancel();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
