package com.example.testapirequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapirequest.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

import static com.example.testapirequest.utils.NetworkUtils.generateURL;

public class MainActivity extends AppCompatActivity {
    private TextView URL_show_tv;
    private EditText city_fied_tv;
    private TextView show_JSON;
    private ProgressBar search_pb;
    public class tryAsync extends AsyncTask<URL, Void, String>
    {

        @Override
        protected void onPreExecute() {
            search_pb = findViewById(R.id.pb_network);
            search_pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String openWeatherres = null;
            search_pb.setVisibility(View.VISIBLE);
            try {
                openWeatherres = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            search_pb.setVisibility(View.INVISIBLE);
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
}
