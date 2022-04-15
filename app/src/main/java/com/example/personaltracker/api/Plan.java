package com.example.personaltracker.api;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Plan { //  Model class

    @SerializedName("activity")
    private String activity;
    @SerializedName("type")
    private String type;

    public Plan() {
    }

    public String getActivity() {
        return activity;
    }

    public String getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString() {
        return "Plan{" +
                "activity='" + activity + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
