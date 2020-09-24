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

import com.example.weather_project.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import static com.example.weather_project.utils.NetworkUtils.generateURL;

public class MainActivity extends AppCompatActivity {
    private TextView URL_show_tv;
    private EditText city_fied_tv;
    private TextView show_JSON;
    private ProgressBar search_pb;
    private RecyclerView forecast_rv;


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
                show_JSON = findViewById(R.id.show_results_tv);
                search_pb.setVisibility(View.INVISIBLE);
                show_JSON.setText(s);
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        search_pb = (ProgressBar) findViewById(R.id.pb_network);
        initRecyclerView();
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
            URL_show_tv = findViewById(R.id.show_URL_id);
            URL_show_tv.setText(url.toString());
            new tryAsync().execute(url);
        }
        return true;
    }
    private void initRecyclerView()
    {
        forecast_rv = (RecyclerView) findViewById(R.id.list_items_rv);
        forecast_rv.setLayoutManager(new LinearLayoutManager(this));
    }

}
