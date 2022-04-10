package com.example.personaltracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personaltracker.api.ApiUtilities;
import com.example.personaltracker.api.Joke;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TextView joke_text;
    private Button button;
    private Joke joke; //can be a List depending on the data fetched
    //for now it is 1 joke

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //This is an asynchronous call
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Status OK", Toast.LENGTH_SHORT).show();

                ApiUtilities.getApiInterface().getJokes()
                        .enqueue(new Callback<Joke>() {
                            @Override
                            public void onResponse(Call<Joke> call, Response<Joke> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Status OK", Toast.LENGTH_SHORT).show();
                                    if (response.body() != null) {
                                        joke = (response.body());
                                    }
                                    joke_text.setText(joke.getJoke());
                                } else {
                                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Joke> call, Throwable t) {
                                joke_text.setText("failllll");
                                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    public void init() {
        joke_text = this.findViewById(R.id.joke_text);
        button = this.findViewById(R.id.button);
        joke = new Joke();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}