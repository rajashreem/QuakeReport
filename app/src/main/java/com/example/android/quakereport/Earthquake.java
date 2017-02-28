package com.example.android.quakereport;

public class Earthquake {
    private final String mMagnitude;
    private final String mLocation;
    private final long mTime;

    public Earthquake(String magnitude, String location, long time){
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getDate() {
        return mTime;
    }
}
