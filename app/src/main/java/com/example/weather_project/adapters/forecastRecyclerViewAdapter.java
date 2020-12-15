package com.example.weather_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather_project.R;
import com.example.weather_project.pojo.WeatherPOJO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class forecastRecyclerViewAdapter extends RecyclerView.Adapter<forecastRecyclerViewAdapter.forecastViewHolder> {
    private List<WeatherPOJO> forecastList = new ArrayList<>();
    private static final String DATE_FORMAT = "dd.MM.YY";
    private OnDayClickListener onDayClickListener;

    public forecastRecyclerViewAdapter(OnDayClickListener onDayClickListener)
    {
        this.onDayClickListener = onDayClickListener;
    }

    public interface  OnDayClickListener{
        void onDayClick(WeatherPOJO weather);
    }


    class forecastViewHolder extends RecyclerView.ViewHolder{
        private TextView item_desc_tv;
        private TextView item_temp_tv;
        private TextView item_date_tv;
        private ImageView item_weather_iv;

        forecastViewHolder(View itemView)
        {
            super(itemView);
            item_desc_tv = itemView.findViewById(R.id.list_item_weather_desc_tv);
            item_temp_tv = itemView.findViewById(R.id.list_item_temperature_tv);
            item_date_tv = itemView.findViewById(R.id.list_item_date_tv);
            item_weather_iv = itemView.findViewById(R.id.weather_icon_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WeatherPOJO weather = forecastList.get(getLayoutPosition());
                    onDayClickListener.onDayClick(weather);
                }
            });
        }




        public void bind(WeatherPOJO weatherPOJO) {
            item_desc_tv.setText(weatherPOJO.getWeather_desc_str());
            item_temp_tv.setText(weatherPOJO.getMain_temp_str());
            item_date_tv.setText(weatherPOJO.getDate_str());
            weatherPOJO.setImages(item_weather_iv);
        }

    }
    public void setItems(Collection<WeatherPOJO> weatherPOJOS)
    {
        forecastList.addAll(weatherPOJOS);
        notifyDataSetChanged();
    }
    public void clearItems()
    {
        forecastList.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public forecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_item, parent, false);
        return  new forecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull forecastViewHolder holder, int position) {
        holder.bind(forecastList.get(position));

    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }
}
