package com.example.weather_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_project.R;
import com.example.weather_project.adapters.forecastRecyclerViewAdapter;
import com.example.weather_project.pojo.WeatherPOJO;
import com.example.weather_project.utils.JSONParser;
import com.example.weather_project.utils.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.weather_project.utils.NetworkUtils.generateURL;

public class MainActivity extends AppCompatActivity {
    private EditText city_fied_tv;
    private ProgressBar search_pb;
    private RecyclerView forecast_rv;
    private ImageView main_weather_iv;
    private TextView min_temp_content_tv;
    private TextView max_temp_content_tv;
    private TextView feels_like_content_tv;
    private TextView pressure_content_tv;
    private TextView humidity_content_tv;
    private TextView main_temp_tv;
    private TextView main_date_tv;
    private TextView sunrise_content_tv;
    private TextView sunset_content_tv;
    private TextView wind_speed_content_tv;
    private forecastRecyclerViewAdapter forecastAdapter;
    private TextView test_tv;
    private JSONParser parser;


    public class tryAsync extends AsyncTask<URL, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            search_pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String openWeatherres = null;
            try {
                openWeatherres = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return openWeatherres;
        }

        @Override
        protected void onPostExecute(String s) {
            parser = new JSONParser(s);
            try {
                parser.parseJSON();
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
            if(s!= null && !s.equals("")){
                search_pb.setVisibility(View.INVISIBLE);
                parser.JSONLog();
                min_temp_content_tv.setText(parser.getPOJOTemp_min(0));
                max_temp_content_tv.setText(parser.getPOJOTemp_max(0));
                pressure_content_tv.setText(parser.getPOJOPressure(0));
                humidity_content_tv.setText(parser.getPOJOHumidity(0));
                feels_like_content_tv.setText(parser.getPOJOFeels_like(0));
                main_temp_tv.setText(parser.getPOJOTemp(0));
                wind_speed_content_tv.setText(parser.getPOJOWind_speed(0));
                main_date_tv.setText(parser.getPOJODate(0));
                sunrise_content_tv.setText(parser.getPOJOSunrise(0));
                sunset_content_tv.setText(parser.getPOJOSunset(0));
                parser.setImages(main_weather_iv, parser.getPOJOWeather_icon(0));
                loadItems();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initRecyclerView();
        search_pb = findViewById(R.id.pb_network);

        min_temp_content_tv = findViewById(R.id.min_temp_content_id);
        max_temp_content_tv = findViewById(R.id.max_temp_content_id);
        feels_like_content_tv = findViewById(R.id.feels_like_content_id);
        pressure_content_tv = findViewById(R.id.pressure_content_id);
        humidity_content_tv = findViewById(R.id.humidity_content_id);
        main_temp_tv = findViewById(R.id.main_temp_id);
        main_date_tv = findViewById(R.id.main_date_id);
        sunrise_content_tv = findViewById(R.id.sunrise_content_id);
        sunset_content_tv = findViewById(R.id.sunset_content_id);
        wind_speed_content_tv = findViewById(R.id.wind_speed_content_id);
        main_weather_iv = findViewById(R.id.main_image_view);
        loadWeatherInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.search_button){
            city_fied_tv = findViewById(R.id.et_city_field);
            URL url = generateURL(city_fied_tv.getText().toString());
            Log.e("TEST", url.toString());
            new tryAsync().execute(url);
        }
        return true;
    }
    private void initRecyclerView()
    {
        forecastRecyclerViewAdapter.OnDayClickListener onDayClickListener = new forecastRecyclerViewAdapter.OnDayClickListener() {
            @Override
            public void onDayClick(WeatherPOJO weather) {
                Toast.makeText(MainActivity.this, "weather" + weather.getWeather_desc_str(), Toast.LENGTH_LONG).show();
            }
        };
        forecast_rv = findViewById(R.id.list_items_rv);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this));
        forecastAdapter = new forecastRecyclerViewAdapter(onDayClickListener);
        forecast_rv.setAdapter(forecastAdapter);
    }
    private void displayWeatherInfo(WeatherPOJO weather)
    {
        min_temp_content_tv.setText(weather.getMin_temp_str());
        max_temp_content_tv.setText(weather.getMax_temp_str());
        feels_like_content_tv.setText(weather.getFeels_like_str());
        pressure_content_tv.setText(weather.getPressure_str());
        humidity_content_tv.setText(weather.getHumidity_str());
        main_temp_tv.setText(weather.getMain_temp_str());
        main_date_tv.setText(weather.getDate_str());
        sunrise_content_tv.setText(weather.getSunrise_str());
        sunset_content_tv.setText(weather.getSunset_str());
        wind_speed_content_tv.setText(weather.getWind_speed_str());

    }

    private void loadWeatherInfo()
    {
        WeatherPOJO weather = getWeather();
        displayWeatherInfo(weather);
    }
    private WeatherPOJO getWeather()
    {
        return new WeatherPOJO("0°C", "0°C","0°C", "0", "0",
                "0°C", "00.00.00", "00:00", "00:00", "0", "Sunny", "01d");
    }
    private void loadItems()
    {
        forecastAdapter.clearItems();
        forecastAdapter.setItems(parser.weatherPOJOS);
    }



}
