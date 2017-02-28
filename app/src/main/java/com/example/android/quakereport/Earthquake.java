package com.example.android.quakereport;

public class Earthquake {
    private final double mMagnitude;
    private final String mLocation;
    private final long mTime;

    public Earthquake(double magnitude, String location, long time){
        mMagnitude = magnitude;
        mLocation = location;
        mTime = time;
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
}
