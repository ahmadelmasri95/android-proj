package com.example.personaltracker.api;

import com.google.gson.annotations.SerializedName;

public class Joke { //  Model class

    @SerializedName("joke")
    private String joke;

    public Joke() {
    }

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
