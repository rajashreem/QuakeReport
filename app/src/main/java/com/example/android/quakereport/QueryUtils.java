package com.example.android.quakereport;

import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private QueryUtils() {
    }

    public static List<Earthquake> extractEarthquakes(String urlString) {
        String jsonResponse = "";

        URL url = createUrl(urlString);

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils", "Error with making http connection", e);
        }

        List<Earthquake> earthquakes = extractEarthquakesFromJsonResponse(jsonResponse);
        return earthquakes;
    }

    @Nullable
    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Problem building the url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");

            connection.connect();

            if(connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("QueryUtils", "Error response code: " + connection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("QueryUtils", "Problem retrieving the earthquake json results", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }


    private static List<Earthquake> extractEarthquakesFromJsonResponse(String jsonResponse) {
        List<Earthquake> earthquakes = new ArrayList<>();

        if(TextUtils.isEmpty(jsonResponse)){
            return earthquakes;
        }

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
