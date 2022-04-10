package com.example.personaltracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    static final String BASE_URL = "https://geek-jokes.sameerkumar.website";

    @GET("/api?format=json")
    Call<Joke> getJokes();
}
