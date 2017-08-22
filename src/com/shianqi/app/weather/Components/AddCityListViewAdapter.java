package com.shianqi.app.weather.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.AddCityListViewHolder;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.InputTipsService;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Service.WeatherService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/8/22.
 */
public class AddCityListViewAdapter extends BaseAdapter {
    private Context context;
    private List<InputTipsService.Tips> tips;
    private LayoutInflater inflater = null;
    private SqlLiteService sqlLiteService;

    public AddCityListViewAdapter(Context context, List<InputTipsService.Tips> tips) {
        this.context = context;
        this.tips = tips;
        sqlLiteService = new SqlLiteService(context);
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public Object getItem(int i) {
        return tips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        AddCityListViewHolder addCityListViewHolder;
        if(view==null){
            addCityListViewHolder = new AddCityListViewHolder();
            view = inflater.inflate(R.layout.add_city_activity_list_item, null);
            addCityListViewHolder.location = (TextView)view.findViewById(R.id.add_city_list_item_location);
            addCityListViewHolder.have_add = (TextView)view.findViewById(R.id.add_city_list_item_have_add);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getWeather(i);
                }
            });
            view.setTag(addCityListViewHolder);
        }else{
            addCityListViewHolder = (AddCityListViewHolder)view.getTag();
        }
        addCityListViewHolder.location.setText(
                ((InputTipsService.Tips)getItem(i)).name + "-" +
                        ((InputTipsService.Tips)getItem(i)).district
        );
        return view;
    }

    public void getWeather(int i) {
        final InputTipsService.Tips tip = tips.get(i);
        WeatherService.getWeatherInfo(context, tip.name, new WeatherService.WeatherCallback() {
            @Override
            public void reject(Exception e) {
                ToastManager.toast(context, "获取天气信息失败，请检查网络连接");
            }

            @Override
            public void resolve(String weatherInfoString) {
                LocationWeatherEntity entity = new LocationWeatherEntity();
                entity.setGd_name(tip.name);
                entity.setGd_district(tip.district);
                entity.setGd_adcode(tip.adcode);
                entity.setHf_weather(weatherInfoString);
                entity.setIsLocation(LocationWeatherEntity.NOT_LOCATION);
                sqlLiteService.saveOrUpdateLocationInfo(entity);
            }
        });
    }
}
