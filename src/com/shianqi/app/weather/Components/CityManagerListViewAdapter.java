package com.shianqi.app.weather.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.CityManagerListViewHolder;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Service.WeatherService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.util.List;

/**
 * Created by admin on 2017/8/23.
 */
public class CityManagerListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = null;
    private SqlLiteService sqlLiteService;
    private Callback callback;
    private List<LocationWeatherEntity> list;

    public CityManagerListViewAdapter(Context context, List<LocationWeatherEntity> list, Callback callback) {
        this.callback = callback;
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    public static interface Callback{
        public void close();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CityManagerListViewHolder holder;
        if(view==null){
            holder = new CityManagerListViewHolder();
            view = inflater.inflate(R.layout.city_manage_activity_list_item, null);

            holder.street = (TextView)view.findViewById(R.id.city_manage_item_street);
            holder.location = (TextView)view.findViewById(R.id.city_manage_item_location);
            holder.temperature = (TextView)view.findViewById(R.id.city_manage_item_temperature);
            holder.temperature_icon = (TextView)view.findViewById(R.id.city_manage_item_temperature_icon);
            holder.weather = (TextView)view.findViewById(R.id.city_manage_item_weather);
            holder.temperature_range = (TextView)view.findViewById(R.id.city_manage_item_temperature_range);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastManager.toast(context, "");
                }
            });

            view.setTag(holder);
        }else{
            holder = (CityManagerListViewHolder)view.getTag();
        }
        WeatherService.WeatherInfo weatherInfo = WeatherService.analysisWeatherInfo(list.get(i).getHf_weather());
        WeatherService.HeWeather5Item heWeather5Item  = weatherInfo.HeWeather5.get(0);

        holder.street.setText(list.get(i).getGd_district());
        holder.location.setText(list.get(i).getLocation());
        holder.temperature.setText(heWeather5Item.now.tmp);
        holder.temperature_icon.setText(heWeather5Item.now.cond.txt);
        holder.weather.setText("空气"+heWeather5Item.getAqiCityQlty()+
                " | 湿度"+heWeather5Item.now.hum+ "%" +
                " | "+heWeather5Item.now.wind.dir+
                heWeather5Item.now.wind.sc+"级");
        holder.temperature_range.setText(
                heWeather5Item.daily_forecast.get(0).tmp.max+"°/" +
                heWeather5Item.daily_forecast.get(0).tmp.min+"°");
        return view;
    }
}
