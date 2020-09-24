package com.example.weather_project;

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

import com.example.weather_project.pojo.WeatherPOJO;
import com.example.weather_project.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

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
            if(s!= null && !s.equals("")){
                search_pb.setVisibility(View.INVISIBLE);
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

        min_temp_content_tv = (TextView) findViewById(R.id.min_temp_content_id);
        max_temp_content_tv = (TextView) findViewById(R.id.max_temp_content_id);
        feels_like_content_tv = (TextView) findViewById(R.id.feels_like_content_id);
        pressure_content_tv = (TextView) findViewById(R.id.pressure_content_id);
        humidity_content_tv = (TextView) findViewById(R.id.humidity_content_id);
        main_temp_tv = (TextView) findViewById(R.id.main_temp_id);
        main_date_tv = (TextView) findViewById(R.id.main_date_id);
        sunrise_content_tv = (TextView) findViewById(R.id.sunrise_content_id);
        sunset_content_tv = (TextView) findViewById(R.id.sunset_content_id);
        wind_speed_content_tv = (TextView) findViewById(R.id.wind_speed_content_id);
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
            new tryAsync().execute(url);
        }
        return true;
    }
    private void initRecyclerView()
    {
        forecast_rv = (RecyclerView) findViewById(R.id.list_items_rv);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this));
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
        return new WeatherPOJO("0°C", "100°C","50°C", "20", "10",
                "40°C", "15.08.2001", "07:00", "21:00", "42");
    }


}
