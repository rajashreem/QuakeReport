package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        TextView magnitude = (TextView) rootView.findViewById(R.id.magnitude);
        magnitude.setText(earthquake.getMagnitude());

        TextView location = (TextView) rootView.findViewById(R.id.location);
        location.setText(earthquake.getLocation());

        TextView date = (TextView) rootView.findViewById(R.id.date);
        date.setText(earthquake.getDate());

        return rootView;
    }
}
