package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import com.shianqi.app.weather.Components.NoScrollListView;
import com.shianqi.app.weather.Service.WeatherService;
import com.shianqi.app.weather.Utils.ToastManager;
import com.shianqi.app.weather.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class PageOne extends Fragment {
    private View view;
    private NoScrollListView listView;
    private ArrayList<HashMap<String,Object>> listItem;
    private SimpleAdapter listAdapter;
    private PullToRefreshScrollView mPullRefreshScrollView;
    //温度
    private TextView tmpTextView;
    //风向
    private TextView dirTextView;
    //风力
    private TextView scTextView;
    //相对湿度
    private TextView humTextView;
    //空气质量
    private TextView weather_qlty;
    //AQI
    private TextView weather_aqi;
    //城市
    private TextView weather_city;
    //概况
    private TextView weather_txt_d;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        listView = (NoScrollListView)view.findViewById(R.id.NoScrollListview);

        tmpTextView = (TextView)view.findViewById(R.id.weather_tmp);
        dirTextView = (TextView)view.findViewById(R.id.weather_dir);
        scTextView = (TextView)view.findViewById(R.id.weather_sc);
        humTextView = (TextView)view.findViewById(R.id.weather_hum);
        weather_qlty = (TextView)view.findViewById(R.id.weather_qlty);
        weather_aqi = (TextView)view.findViewById(R.id.weather_aqi);
        weather_city = (TextView)view.findViewById(R.id.weather_city);
        weather_txt_d = (TextView)view.findViewById(R.id.weather_txt_d);


        listView.setFocusable(false);
        listItem = new ArrayList<HashMap<String, Object>>();

        listAdapter = new SimpleAdapter(getActivity(),listItem,
                R.layout.list_items,
                new String[]{
                        "list_item_date",
                        "list_item_beginning_time",
                        "list_item_time",
                        "list_item_score"
                },
                new int[]{
                        R.id.list_item_date,
                        R.id.list_item_beginning_time,
                        R.id.list_item_time,
                        R.id.list_item_score
                });

        listView.setAdapter(listAdapter);

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
                //ToastManager.toast(getActivity(), "success!");
            }
        });

        WeatherService.WeatherInfo weatherInfo = WeatherService.getCacheWeatherInfo(getActivity());
        if(weatherInfo!=null){
            syncWeatherInfo(weatherInfo);
        }
        return view;
    }

    private void getDate(){

    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            getWeatherInfo();
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here
            // Call onRefreshComplete when the list has been refreshed.
            super.onPostExecute(result);
        }
    }

    /**
     * 获取温度信息
     */
    public void getWeatherInfo(){
        WeatherService.getWeatherInfo(getActivity(), "shenyang", new WeatherService.WeatherCallback() {
            @Override
            public void reject(Exception e) {
                ToastManager.toast(getActivity(), "信息获取失败，请稍后尝试");
                //同步失败后收回下拉菜单
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void resolve(WeatherService.WeatherInfo weatherInfo) {
                syncWeatherInfo(weatherInfo);
            }
        });
    }

    /**
     * 将温度信息同步到界面
     * @param weatherInfo 温度信息
     */
    public void syncWeatherInfo(WeatherService.WeatherInfo weatherInfo){
        List<WeatherService.HeWeather5Item> list = weatherInfo.HeWeather5;
        WeatherService.HeWeather5Item heWeather5Item  = list.get(0);

        Iterator<WeatherService.DailyForecastItem> iterator =  heWeather5Item.daily_forecast.iterator();
        listItem.clear();
        while (iterator.hasNext()){
            WeatherService.DailyForecastItem item = iterator.next();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("list_item_date", item.date);
            map.put("list_item_beginning_time", item.cond.txt_d);
            map.put("list_item_time", "降水概率:" + item.pop + "%");
            map.put("list_item_score", item.tmp.min + "/" + item.tmp.max);
            listItem.add(map);
        }
        listAdapter.notifyDataSetChanged();


        tmpTextView.setText(heWeather5Item.now.tmp + "°");
        dirTextView.setText(heWeather5Item.now.wind.dir);
        scTextView.setText(heWeather5Item.now.wind.sc + "级");
        humTextView.setText(heWeather5Item.now.hum + "%");
        weather_qlty.setText(heWeather5Item.aqi.city.qlty);
        weather_aqi.setText(heWeather5Item.aqi.city.aqi);
        weather_city.setText(heWeather5Item.basic.city + " | ");
        weather_txt_d.setText(heWeather5Item.now.cond.txt);

        //同步完成后收回下拉菜单
        mPullRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
