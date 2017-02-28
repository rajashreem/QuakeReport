package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    final String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if (rootView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.single_earthquake_report, parent, false);
        }

        Earthquake earthquake = getItem(position);

        setMagnitude(rootView, earthquake);
        setLocation(rootView, earthquake);
        setDateAndTime(rootView, earthquake);

        return rootView;
    }

    private void setMagnitude(View rootView, Earthquake earthquake) {
        double magnitude = earthquake.getMagnitude();
        DecimalFormat formatter = new DecimalFormat("0.0");

        TextView magnitudeText = (TextView) rootView.findViewById(R.id.magnitude);
        magnitudeText.setText(formatter.format(magnitude));
    }

    private void setLocation(View rootView, Earthquake earthquake) {
        String locationOffset, primaryLocation;

        String originalLocation = earthquake.getLocation();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] dividedLocations = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = dividedLocations[0] + LOCATION_SEPARATOR;
            primaryLocation = dividedLocations[1];
        } else {
            locationOffset = "Near the";
            primaryLocation = originalLocation;
        }

        TextView offsetLocationText = (TextView) rootView.findViewById(R.id.location_offset);
        offsetLocationText.setText(locationOffset);

        TextView primaryLocationText = (TextView) rootView.findViewById(R.id.primary_location);
        primaryLocationText.setText(primaryLocation);
    }

    private void setDateAndTime(View rootView, Earthquake earthquake) {
        Date date = new Date(earthquake.getTime());

        String formattedDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
        TextView dateText = (TextView) rootView.findViewById(R.id.date);
        dateText.setText(formattedDate);

        String formattedTime = new SimpleDateFormat("h:mm a").format(date);
        TextView timeText = (TextView) rootView.findViewById(R.id.time);
        timeText.setText(formattedTime);
    }
}