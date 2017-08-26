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
import android.widget.*;
import com.shianqi.app.weather.Components.CityManagerListViewAdapter;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Service.WeatherService;

import java.util.*;

/**
 * 城市管理界面
 * Created by admin on 2017/8/19.
 */
public class CityManageActivity extends Activity {
    private LinearLayout back;
    private TextView backTextView;
    private Button add_city_button;
    private ListView listView;
    private CityManagerListViewAdapter adapter;
    private List listItem;
    private SqlLiteService sqlLiteService;
    private TextView city_manage_delete;
    private Set<Integer> removeSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_manage_activity);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");

        sqlLiteService = new SqlLiteService(CityManageActivity.this);

        back = (LinearLayout)findViewById(R.id.city_manage_back);
        backTextView = (TextView)findViewById(R.id.city_manage_back_text);
        add_city_button = (Button)findViewById(R.id.city_manage_add_city_button);
        city_manage_delete = (TextView)findViewById(R.id.city_manage_delete);

        backTextView.setTypeface(iconfont);
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

        removeSet = new HashSet<Integer>();

        city_manage_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.isEditable()){
                    Iterator<Integer> iterator = removeSet.iterator();
                    List<LocationWeatherEntity> list = new ArrayList<LocationWeatherEntity>();
                    while(iterator.hasNext()){
                        int i = iterator.next();
                        LocationWeatherEntity entity = (LocationWeatherEntity) listItem.get(i);
                        Log.e("REMOVE", entity.getFormatted_address());
                        if(!WeatherService.getCacheWeatherILnfoId(CityManageActivity.this).equals(entity.getFormatted_address())){
                            list.add(entity);
                            sqlLiteService.removeLocationInfo(entity.getFormatted_address());
                        }
                    }
                    listItem.removeAll(list);
                    city_manage_delete.setText("编辑");
                    city_manage_delete.setBackgroundResource(R.drawable.shape_border_button);
                    adapter.setEditable(false);
                    removeSet.clear();
                    adapter.notifyDataSetChanged();
                }else{
                    city_manage_delete.setText("删除");
                    city_manage_delete.setBackgroundResource(R.drawable.shape_border_button_red);
                    adapter.setEditable(true);
                    removeSet.clear();
                }
            }
        });

        listItem = new ArrayList();
        listView = (ListView)findViewById(R.id.city_manage_listView);
        adapter = new CityManagerListViewAdapter(
                CityManageActivity.this,
                listItem,
                new CityManagerListViewAdapter.Callback() {
                    @Override
                    public void close() {
                        exit();
                    }

                    @Override
                    public void addIndex(int index) {
                        removeSet.add(index);
                    }

                    @Override
                    public void removeIndex(int index) {
                        removeSet.remove(index);
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
            exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        CityManageActivity.this.finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }
}
