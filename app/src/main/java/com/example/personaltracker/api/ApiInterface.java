package com.example.personaltracker.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    static final String BASE_URL = "https://image.thum.io/get/auth/57066-Masri/png/";

    @GET
    Call<ResponseBody> getScreenshot(@Url String url);
}
