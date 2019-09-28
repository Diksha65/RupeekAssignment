package com.example.rupeekassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("url")
    @Expose
    private String url;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Datum(String place, String url) {
        this.place = place;
        this.url = url;
    }

}
