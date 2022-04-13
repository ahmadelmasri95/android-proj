package com.example.personaltracker.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    static final String BASE_URL = "https://www.boredapi.com/api/";

    @GET
    Call<Plan> getPlan(@Url String url);
}
