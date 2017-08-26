package com.shianqi.app.weather.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.AddCityListViewHolder;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.InputTipsService;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Service.WeatherService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.util.List;

/**
 * Created by admin on 2017/8/22.
 */
public class AddCityListViewAdapter extends BaseAdapter {
    private Context context;
    private List<InputTipsService.Tips> tips;
    private LayoutInflater inflater = null;
    private SqlLiteService sqlLiteService;
    private Callback callback;

    public static interface Callback{
        public void close();
    }

    public AddCityListViewAdapter(Context context, List<InputTipsService.Tips> tips, Callback callback) {
        this.context = context;
        this.tips = tips;
        this.callback = callback;
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
                ((InputTipsService.Tips)getItem(i)).formatted_address
        );
        return view;
    }

    public void getWeather(int i) {
        final InputTipsService.Tips tip = tips.get(i);
        WeatherService.getWeatherInfo(context, tip.getCity(), new WeatherService.WeatherCallback() {
            @Override
            public void reject(Exception e) {
                ToastManager.toast(context, "获取天气信息失败，请检查网络连接");
            }

            @Override
            public void resolve(String weatherInfoString) {
                WeatherService.WeatherInfo weatherInfo = WeatherService.analysisWeatherInfo(weatherInfoString);
                ToastManager.toast(context, weatherInfo.HeWeather5.get(0).status);
                if(weatherInfo.HeWeather5.get(0).status.equals("ok")){
                    LocationWeatherEntity entity = new LocationWeatherEntity();
                    entity.setFormatted_address(tip.formatted_address);
                    entity.setGd_province(tip.province);
                    entity.setGd_city(tip.getCity());
                    entity.setGd_district(tip.getDistrict());
                    entity.setLocation(tip.location);
                    entity.setHf_weather(weatherInfoString);
                    sqlLiteService.saveOrUpdateLocationInfo(entity);
                    callback.close();
                }else{
                    ToastManager.toast(context, "暂无此城市天气数据");
                }
            }
        });
    }
}
