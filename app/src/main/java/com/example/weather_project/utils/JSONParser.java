package com.example.weather_project.utils;

import android.util.Log;
import android.widget.ImageView;

import com.example.weather_project.R;
import com.example.weather_project.pojo.WeatherPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class JSONParser {
    private String url;
    private String cod = null;
    private String message = null;
    private String cnt = null;
    private String list_dt = null;
    private String main_temp = null;
    private String main_feels_like = null;
    private String main_temp_min = null;
    private String main_temp_max = null;
    private String main_pressure = null;
    private String main_sea_level = null;
    private String main_grnd_level = null;
    private String main_humidity = null;
    private String main_temp_kf = null;
    private String weather_id = null;
    private String weather_main = null;
    private String weather_description = null;
    private String weather_icon = null;
    private String clouds_all = null;
    private String wind_speed = null;
    private String wind_deg = null;
    private String visibility = null;
    private String pop = null;
    private String sys_pod = null;
    private String dt_txt = null;
    private String city_id = null;
    private String city_name = null;
    private String coord_lat = null;
    private String coord_lon = null;
    private String city_country = null;
    private String city_population = null;
    private String city_timezone = null;
    private String city_sunrise = null;
    private String city_sunset = null;
    public ArrayList<WeatherPOJO> weatherPOJOS = new ArrayList<>();


    public JSONParser(String url) {
        this.url = url;
    }

    public void parseJSON() throws JSONException, ParseException {
        weatherPOJOS.clear();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        JSONObject jsonResponse = new JSONObject(url);
        cod = jsonResponse.getString("cod");
        message = jsonResponse.getString("message");
        cnt = jsonResponse.getString("cnt");
        JSONArray listArray = jsonResponse.getJSONArray("list");
        for(int i = 0; i < 40; i+=8) {
            JSONObject listInfo = listArray.getJSONObject(i);
            list_dt = listInfo.getString("dt");
            JSONArray weatherInfoArray = listInfo.getJSONArray("weather");
            JSONObject weatherInfoObject = weatherInfoArray.getJSONObject(0);
            JSONObject mainInfo = listInfo.getJSONObject("main");
            main_temp = mainInfo.getString("temp") + "°C";
            main_feels_like = mainInfo.getString("feels_like") + "°C";
            main_temp_min = mainInfo.getString("temp_min") + "°C";
            main_temp_max = mainInfo.getString("temp_max") + "°C";
            main_pressure = mainInfo.getString("pressure");
            main_sea_level = mainInfo.getString("sea_level");
            main_grnd_level = mainInfo.getString("grnd_level");
            main_humidity = mainInfo.getString("humidity");
            main_temp_kf = mainInfo.getString("temp_kf");
            weather_id = weatherInfoObject.getString("id");
            weather_main = weatherInfoObject.getString("main");
            weather_description = weatherInfoObject.getString("description");
            weather_icon = weatherInfoObject.getString("icon");
            JSONObject cloudsInfo = listInfo.getJSONObject("clouds");
            clouds_all = cloudsInfo.getString("all");
            JSONObject windInfo = listInfo.getJSONObject("wind");
            wind_speed = windInfo.getString("speed");
            wind_deg = windInfo.getString("deg");
            visibility = listInfo.getString("visibility");
            pop = listInfo.getString("pop");
            JSONObject sysInfo = listInfo.getJSONObject("sys");
            sys_pod = sysInfo.getString("pod");
            dt_txt = dateFormat.format(Objects.requireNonNull(oldDateFormat.parse(listInfo.getString("dt_txt"))));
            JSONObject cityInfo = jsonResponse.getJSONObject("city");
            city_id = cityInfo.getString("id");
            city_name = cityInfo.getString("name");
            JSONObject coordInfo = cityInfo.getJSONObject("coord");
            coord_lat = coordInfo.getString("lat");
            coord_lon = coordInfo.getString("lon");
            city_country = cityInfo.getString("country");
            city_population = cityInfo.getString("population");
            city_timezone = cityInfo.getString("timezone");
            city_sunrise = timeFormat.format(new Date(cityInfo.getInt("sunrise") * 1000).getTime());
            city_sunset = timeFormat.format(new Date(cityInfo.getInt("sunset") * 1000).getTime());
            weatherPOJOS.add(new WeatherPOJO(main_temp_min, main_temp_max, main_feels_like, main_pressure, main_humidity,
                    main_temp, dt_txt, city_sunrise, city_sunset, wind_speed, weather_description, weather_icon));
        }
    }

    public void JSONLog()
    {
        Log.i("JSON", cod + " " + message + " " + cnt + " " + list_dt + " " + main_temp + " " + main_feels_like + " " + main_temp_min
                + " " + main_temp_max + " " + main_pressure + " " + main_sea_level + " " + main_grnd_level + main_humidity + " " + main_temp_kf
                + " " + weather_id + " " + weather_main + " " + weather_description + " " + weather_icon + " " + clouds_all + " " + wind_speed
                + " " + wind_deg + " " + visibility + " " + pop + " " + sys_pod + " " + dt_txt + " " + city_id + " " + city_name + " " + coord_lat
                + " " + coord_lon + " " + city_country + " " + city_population + " " + city_timezone + " " + city_sunrise + " " + city_sunset);
    }


    public String getPOJODate(int i )
    {
        return weatherPOJOS.get(i).getDate_str();
    }
    public String getPOJOPressure(int i )
    {
        return weatherPOJOS.get(i).getPressure_str();
    }
    public String getPOJOHumidity(int i )
    {
        return weatherPOJOS.get(i).getHumidity_str();
    }
    public String getPOJOTemp(int i )
    {
        return weatherPOJOS.get(i).getMain_temp_str();
    }
    public String getPOJOTemp_min(int i )
    {
        return weatherPOJOS.get(i).getMin_temp_str();
    }
    public String getPOJOTemp_max(int i )
    {
        return weatherPOJOS.get(i).getMax_temp_str();
    }
    public String getPOJOFeels_like(int i )
    {
        return weatherPOJOS.get(i).getFeels_like_str();
    }
    public String getPOJOSunrise(int i )
    {
        return weatherPOJOS.get(i).getSunrise_str();
    }
    public String getPOJOSunset(int i )
    {
        return weatherPOJOS.get(i).getSunset_str();
    }
    public String getPOJOWind_speed(int i )
    {
        return weatherPOJOS.get(i).getWind_speed_str();
    }
    public String getPOJOWeather_icon(int i )
    {
        return weatherPOJOS.get(i).getWeather_image_str();
    }
    public String getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    public String getCnt() {
        return cnt;
    }

    public String getList_dt() {
        return list_dt;
    }

    public String getMain_temp() {
        return main_temp;
    }

    public String getMain_feels_like() {
        return main_feels_like;
    }

    public String getMain_temp_min() {
        return main_temp_min;
    }

    public String getMain_temp_max() {
        return main_temp_max;
    }

    public String getMain_pressure() {
        return main_pressure;
    }

    public String getMain_sea_level() {
        return main_sea_level;
    }

    public String getMain_grnd_level() {
        return main_grnd_level;
    }

    public String getMain_humidity() {
        return main_humidity;
    }

    public String getMain_temp_kf() {
        return main_temp_kf;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public String getClouds_all() {
        return clouds_all;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getPop() {
        return pop;
    }

    public String getSys_pod() {
        return sys_pod;
    }

    public String getDt_txt() {
        return dt_txt;
    }
    public String getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCoord_lat() {
        return coord_lat;
    }

    public String getCoord_lon() {
        return coord_lon;
    }

    public String getCity_country() {
        return city_country;
    }

    public String getCity_population() {
        return city_population;
    }

    public String getCity_timezone() {
        return city_timezone;
    }

    public String getCity_sunrise() {
        return city_sunrise;
    }

    public String getCity_sunset() {
        return city_sunset;
    }
    public void setImages(ImageView test, String icon)
    {
        switch(icon) {
            case ("01d"): test.setImageResource(R.drawable.x01d);
                break;
            case ("02d"): test.setImageResource(R.drawable.x02d);
                break;
            case ("03d"): test.setImageResource(R.drawable.x03d);
                break;
            case ("04d"): test.setImageResource(R.drawable.x04d);
                break;
            case ("09d"): test.setImageResource(R.drawable.x09d);
                break;
            case ("10d"): test.setImageResource(R.drawable.x10d);
                break;
            case ("11d"): test.setImageResource(R.drawable.x11d);
                break;
            case ("13d"): test.setImageResource(R.drawable.x13d);
                break;
            case ("50d"): test.setImageResource(R.drawable.x50d);
                break;
            case ("01n"): test.setImageResource(R.drawable.x01n);
                break;
            case ("02n"): test.setImageResource(R.drawable.x02n);
                break;
            case ("03n"): test.setImageResource(R.drawable.x03n);
                break;
            case ("04n"): test.setImageResource(R.drawable.x04n);
                break;
            case ("09n"): test.setImageResource(R.drawable.x09n);
                break;
            case ("10n"): test.setImageResource(R.drawable.x10n);
                break;
            case ("11n"): test.setImageResource(R.drawable.x11n);
                break;
            case ("13n"): test.setImageResource(R.drawable.x13n);
                break;
            case ("50n"): test.setImageResource(R.drawable.x50n);
                break;
        }
    }
}