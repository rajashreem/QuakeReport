package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QueryUtils {
    private static final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=10";

    private QueryUtils() {
    }

    public static ArrayList<Earthquake> extractEarthquakes() throws IOException {
        ArrayList<Earthquake> earthquakes;

        String jsonResponse = "";

        jsonResponse = makeHttpRequest(URL);
        earthquakes = extractEarthquakesFromJsonResponse(jsonResponse);

        return earthquakes;
    }

    private static String makeHttpRequest(String urlString) throws IOException {
        String jsonResponse = "";
        HttpURLConnection connection = null;
        InputStream response = null;
        URL url = null;

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(1000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");

            connection.connect();

            response = connection.getInputStream();
            jsonResponse = getJsonReponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if(response != null){
                response.close();
            }
        }

        return jsonResponse;
    }

    private static String getJsonReponse(InputStream response) {
        InputStreamReader inputStreamReader = new InputStreamReader(response);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder builder = new StringBuilder();
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


    private static ArrayList<Earthquake> extractEarthquakesFromJsonResponse(String jsonResponse) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject rootResponse = new JSONObject(jsonResponse);
            JSONArray earthquakeList = rootResponse.optJSONArray("features");

            for (int i = 0; i < earthquakeList.length(); i++) {
                JSONObject properties = earthquakeList.getJSONObject(i).getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");

                Earthquake quake = new Earthquake(magnitude, place, time, url);
                earthquakes.add(quake);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }
}
