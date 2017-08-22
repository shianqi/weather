package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Service.WeatherService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市管理界面
 * Created by admin on 2017/8/19.
 */
public class CityManageActivity extends Activity {
    private TextView back;
    private Button add_city_button;
    private ListView listView;
    private SimpleAdapter adapter;
    private ArrayList listItem;
    private SqlLiteService sqlLiteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_manage_activity);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");

        sqlLiteService = new SqlLiteService(CityManageActivity.this);

        back = (TextView)findViewById(R.id.city_manage_back);
        add_city_button = (Button)findViewById(R.id.city_manage_add_city_button);

        back.setTypeface(iconfont);
        add_city_button.setTypeface(iconfont);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityManageActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            }
        });
        add_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CityManageActivity.this, AddCityActivity.class);
                CityManageActivity.this.startActivity(intent);
                CityManageActivity.this.overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
            }
        });
        listItem = new ArrayList<HashMap<String, Object>>();

        List<LocationWeatherEntity> locationWeatherEntities =  sqlLiteService.getAllLocationInfo();
        for(int i = 0; i<locationWeatherEntities.size();i++) {
            WeatherService.WeatherInfo weatherInfo = WeatherService.analysisWeatherInfo(locationWeatherEntities.get(i).getHf_weather());
            WeatherService.HeWeather5Item heWeather5Item  = weatherInfo.HeWeather5.get(0);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("city_manage_item_street", heWeather5Item.basic.city);
            map.put("city_manage_item_location", heWeather5Item.basic.cnty);
            map.put("city_manage_item_temperature",heWeather5Item.now.tmp+"°");
            map.put("city_manage_item_temperature_icon", heWeather5Item.now.cond.txt);
            map.put(
                    "city_manage_item_weather", "空气"+heWeather5Item.aqi.city.qlty+
                            " | 湿度"+heWeather5Item.now.hum+ "%" +
                            " | "+heWeather5Item.now.wind.dir+
                            heWeather5Item.now.wind.sc+"级");
            map.put("city_manage_item_temperature_range","" +
                    heWeather5Item.daily_forecast.get(0).tmp.max+"°/" +
                    heWeather5Item.daily_forecast.get(0).tmp.min+"°");
            listItem.add(map);
        }

        listView = (ListView)findViewById(R.id.city_manage_listView);
        adapter = new SimpleAdapter(CityManageActivity.this,
                listItem,
                R.layout.city_manage_activity_list_item,
                new String[]{
                        "city_manage_item_street",
                        "city_manage_item_location",
                        "city_manage_item_temperature",
                        "city_manage_item_temperature_icon",
                        "city_manage_item_weather",
                        "city_manage_item_temperature_range"
                },
                new int[]{
                        R.id.city_manage_item_street,
                        R.id.city_manage_item_location,
                        R.id.city_manage_item_temperature,
                        R.id.city_manage_item_temperature_icon,
                        R.id.city_manage_item_weather,
                        R.id.city_manage_item_temperature_range
                });
        listView.setAdapter(adapter);

        initState();
    }

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // TODO: 2017/8/22
        ToastManager.toast(CityManageActivity.this, "!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            CityManageActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
        }
        return super.onKeyDown(keyCode, event);
    }
}
