package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Components.CityManagerListViewAdapter;
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
    private CityManagerListViewAdapter adapter;
    private List listItem;
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
        listItem = new ArrayList();
        listView = (ListView)findViewById(R.id.city_manage_listView);
        adapter = new CityManagerListViewAdapter(CityManageActivity.this, listItem, new CityManagerListViewAdapter.Callback() {
            @Override
            public void close() {

            }
        });
        listView.setAdapter(adapter);

        getDate();
        initState();
    }

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    private void getDate() {
        listItem.clear();
        List<LocationWeatherEntity> locationWeatherEntities =  sqlLiteService.getAllLocationInfo();
        for(int i=0;i<locationWeatherEntities.size();i++){
            listItem.add(locationWeatherEntities.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
