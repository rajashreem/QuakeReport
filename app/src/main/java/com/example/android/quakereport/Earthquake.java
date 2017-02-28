package com.example.android.quakereport;

public class Earthquake {
    private final double mMagnitude;
    private final String mLocation;
    private final long mTime;
    private final String mUrl;

    public Earthquake(double magnitude, String location, long time, String url){
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
        mUrl = url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTime() {
        return mTime;
    }

    public String getUrl() {
        return mUrl;
    }
}
