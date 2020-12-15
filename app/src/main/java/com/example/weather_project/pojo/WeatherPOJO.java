package com.example.weather_project.pojo;

import android.widget.ImageView;

import androidx.core.view.accessibility.AccessibilityViewCommand;

import com.example.weather_project.R;

import java.util.Objects;

public class WeatherPOJO {
    private String min_temp_str;
    private String max_temp_str;
    private String feels_like_str;
    private String pressure_str;
    private String humidity_str;
    private String main_temp_str;
    private String date_str;
    private String sunrise_str;
    private String sunset_str;
    private String wind_speed_str;
    private String weather_desc_str;
    private String weather_image_str;

    public WeatherPOJO(String min_temp_str, String max_temp_str, String feels_like_str, String pressure_str, String humidity_str,
                       String main_temp_str, String date_str, String sunrise_str, String sunset_str, String wind_speed_str, String weather_desc_str, String weather_image_str) {
        this.min_temp_str = min_temp_str;
        this.max_temp_str = max_temp_str;
        this.feels_like_str = feels_like_str;
        this.pressure_str = pressure_str;
        this.humidity_str = humidity_str;
        this.main_temp_str = main_temp_str;
        this.date_str = date_str;
        this.sunrise_str = sunrise_str;
        this.sunset_str = sunset_str;
        this.wind_speed_str = wind_speed_str;
        this.weather_desc_str = weather_desc_str;
        this.weather_image_str = weather_image_str;
    }
    public void setImages(ImageView test) {
        switch (weather_image_str) {
            case ("01d"):
                test.setImageResource(R.drawable.x01d);
                break;
            case ("02d"):
                test.setImageResource(R.drawable.x02d);
                break;
            case ("03d"):
                test.setImageResource(R.drawable.x03d);
                break;
            case ("04d"):
                test.setImageResource(R.drawable.x04d);
                break;
            case ("09d"):
                test.setImageResource(R.drawable.x09d);
                break;
            case ("10d"):
                test.setImageResource(R.drawable.x10d);
                break;
            case ("11d"):
                test.setImageResource(R.drawable.x11d);
                break;
            case ("13d"):
                test.setImageResource(R.drawable.x13d);
                break;
            case ("50d"):
                test.setImageResource(R.drawable.x50d);
                break;
            case ("01n"):
                test.setImageResource(R.drawable.x01n);
                break;
            case ("02n"):
                test.setImageResource(R.drawable.x02n);
                break;
            case ("03n"):
                test.setImageResource(R.drawable.x03n);
                break;
            case ("04n"):
                test.setImageResource(R.drawable.x04n);
                break;
            case ("09n"):
                test.setImageResource(R.drawable.x09n);
                break;
            case ("10n"):
                test.setImageResource(R.drawable.x10n);
                break;
            case ("11n"):
                test.setImageResource(R.drawable.x11n);
                break;
            case ("13n"):
                test.setImageResource(R.drawable.x13n);
                break;
            case ("50n"):
                test.setImageResource(R.drawable.x50n);
                break;
        }
    }
    public String getMin_temp_str() {
        return min_temp_str;
    }

    public String getWeather_image_str() {
        return weather_image_str;
    }
    public String getMax_temp_str() {
        return max_temp_str;
    }

    public String getFeels_like_str() {
        return feels_like_str;
    }

    public String getPressure_str() {
        return pressure_str;
    }

    public String getHumidity_str() {
        return humidity_str;
    }

    public String getMain_temp_str() {
        return main_temp_str;
    }

    public String getDate_str() {
        return date_str;
    }

    public String getSunrise_str() {
        return sunrise_str;
    }

    public String getSunset_str() {
        return sunset_str;
    }

    public String getWind_speed_str() {
        return wind_speed_str;
    }

    public String getWeather_desc_str() { return weather_desc_str; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherPOJO weatherPOJO = (WeatherPOJO) o;
        return min_temp_str.equals(weatherPOJO.min_temp_str) &&
                max_temp_str.equals(weatherPOJO.max_temp_str) &&
                feels_like_str.equals(weatherPOJO.feels_like_str) &&
                pressure_str.equals(weatherPOJO.pressure_str) &&
                humidity_str.equals(weatherPOJO.humidity_str) &&
                main_temp_str.equals(weatherPOJO.main_temp_str) &&
                date_str.equals(weatherPOJO.date_str) &&
                sunrise_str.equals(weatherPOJO.sunrise_str) &&
                sunset_str.equals(weatherPOJO.sunset_str) &&
                wind_speed_str.equals(weatherPOJO.wind_speed_str) &&
                weather_desc_str.equals(weatherPOJO.weather_desc_str)&&
                weather_image_str.equals(weatherPOJO.weather_image_str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min_temp_str, max_temp_str, feels_like_str, pressure_str, humidity_str, main_temp_str, date_str, sunrise_str, sunset_str, wind_speed_str, weather_desc_str, weather_image_str);
    }
}
