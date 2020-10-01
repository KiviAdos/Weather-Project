package com.example.weather_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
                min_temp_content_tv.setText(parser.getMain_temp_min());
                max_temp_content_tv.setText(parser.getMain_temp_max());
                pressure_content_tv.setText(parser.getMain_pressure());
                humidity_content_tv.setText(parser.getMain_humidity());
                feels_like_content_tv.setText(parser.getMain_feels_like());
                main_temp_tv.setText(parser.getMain_temp());
                wind_speed_content_tv.setText(parser.getWind_speed());
                main_date_tv.setText(parser.getDt_txt());
                sunrise_content_tv.setText(parser.getCity_sunrise());
                sunset_content_tv.setText(parser.getCity_sunset());
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
        loadWeatherInfo();
        loadItems();
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
            test_tv = findViewById(R.id.test_tv);
            test_tv.setText(url.toString());
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
                "0°C", "00.00.00", "00:00", "00:00", "0", "Sunny");
    }
    private void loadItems()
    {
        Collection<WeatherPOJO> weatherPOJOS = getWeatherInfo();
        forecastAdapter.setItems(weatherPOJOS);
    }
    private Collection<WeatherPOJO> getWeatherInfo()
    {
        return Arrays.asList(
                new WeatherPOJO("0°C", "100°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Sunny"),
                new WeatherPOJO("0°C", "50°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Windy"),
                new WeatherPOJO("0°C", "50°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Rainy"),
                new WeatherPOJO("0°C", "50°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Cloudy"),
                new WeatherPOJO("0°C", "50°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Snowy"),
                new WeatherPOJO("0°C", "50°C","50°C", "20", "10",
                        "40°C", "15.08.2001", "07:00", "21:00", "42", "Foggy"));


    }



}
