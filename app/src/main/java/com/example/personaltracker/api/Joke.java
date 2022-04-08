package com.example.personaltracker.api;

import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("joke")
    private String joke;

    public Joke(String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

}
