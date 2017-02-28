package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{
    public EarthquakeAdapter(Context context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if(rootView == null){
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.single_earthquake_report, parent, false);
        }

        Earthquake earthquake = getItem(position);

        setMagnitude(rootView, earthquake);
        setLocation(rootView, earthquake);
        setDateAndTime(rootView, earthquake);

        return rootView;
    }

    private void setMagnitude(View rootView, Earthquake earthquake) {
        TextView magnitude = (TextView) rootView.findViewById(R.id.magnitude);
        magnitude.setText(earthquake.getMagnitude());
    }

    private void setLocation(View rootView, Earthquake earthquake) {
        TextView location = (TextView) rootView.findViewById(R.id.location);
        location.setText(earthquake.getLocation());
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
