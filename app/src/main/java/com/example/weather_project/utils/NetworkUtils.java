package com.example.weather_project.utils;

import android.net.Uri;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String OW_API_BASE_URL = "https://api.openweathermap.org/";
    private static final String OW_API_DATA_URL = "data/2.5/forecast";
    private static final String OW_API_PARAM_Q = "q";
    private static final String OW_API_PARAM_CNT = "cnt";
    private static final String OW_API_PARAM_UNITS = "units";
    private static final String OW_API_UNITS = "metric";
    private static final String OW_API_CNT = "40";
    private static final String OW_API_PARAM_APPID = "appid";
    private static final String OW_API_APPID = "131f0e7a6c3e5e477f646fbaf962ffa2";
    public static URL generateURL(String city_name){
        Uri builtUri = Uri.parse(OW_API_BASE_URL + OW_API_DATA_URL).buildUpon()
                .appendQueryParameter(OW_API_PARAM_Q, city_name)
                .appendQueryParameter(OW_API_PARAM_CNT, OW_API_CNT)
                .appendQueryParameter(OW_API_PARAM_UNITS, OW_API_UNITS)
                .appendQueryParameter(OW_API_PARAM_APPID, OW_API_APPID)
                .build();

        URL finalURL = null;
        try{
            finalURL = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return finalURL;
    }
//    Open connection for read from URL
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
