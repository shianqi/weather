package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import com.shianqi.app.weather.Components.FutureWeatherListViewAdapter;
import com.shianqi.app.weather.Components.NoScrollListView;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;
import com.shianqi.app.weather.Service.InputTipsService;
import com.shianqi.app.weather.Service.PositionService;
import com.shianqi.app.weather.Service.SqlLiteService;
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
    private List<WeatherService.DailyForecastItem> listItem;
    private FutureWeatherListViewAdapter listAdapter;
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

    private WebView webview;

    private Button add_position;

    private TextView suggest_item_title1;
    private TextView suggest_item_title2;
    private TextView suggest_item_title3;
    private TextView suggest_item_title4;
    private TextView suggest_item_title5;
    private TextView suggest_item_title6;
    private TextView suggest_item_title7;
    private TextView suggest_item_title8;

    private TextView suggest_item_text1;
    private TextView suggest_item_text2;
    private TextView suggest_item_text3;
    private TextView suggest_item_text4;
    private TextView suggest_item_text5;
    private TextView suggest_item_text6;
    private TextView suggest_item_text7;
    private TextView suggest_item_text8;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);

        findView(view);
        configWebView();
        initListView();
        setListener();
        getWeatherInfoByCache();
        return view;
    }

    private void findView(View view) {
        Typeface numberTypeface = Typeface.createFromAsset(getActivity().getAssets(), "iconfont/number.ttf");
        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf");

        listView = (NoScrollListView)view.findViewById(R.id.NoScrollListview);
        tmpTextView = (TextView)view.findViewById(R.id.weather_tmp);
        dirTextView = (TextView)view.findViewById(R.id.weather_dir);
        scTextView = (TextView)view.findViewById(R.id.weather_sc);
        humTextView = (TextView)view.findViewById(R.id.weather_hum);
        weather_qlty = (TextView)view.findViewById(R.id.weather_qlty);
        weather_aqi = (TextView)view.findViewById(R.id.weather_aqi);
        weather_city = (TextView)view.findViewById(R.id.weather_city);
        weather_txt_d = (TextView)view.findViewById(R.id.weather_txt_d);
        webview = (WebView)view. findViewById(R.id.weather_webview);
        add_position = (Button)view.findViewById(R.id.add_position);

        suggest_item_title1 = (TextView)view.findViewById(R.id.suggest_item_title1);
        suggest_item_title2 = (TextView)view.findViewById(R.id.suggest_item_title2);
        suggest_item_title3 = (TextView)view.findViewById(R.id.suggest_item_title3);
        suggest_item_title4 = (TextView)view.findViewById(R.id.suggest_item_title4);
        suggest_item_title5 = (TextView)view.findViewById(R.id.suggest_item_title5);
        suggest_item_title6 = (TextView)view.findViewById(R.id.suggest_item_title6);
        suggest_item_title7 = (TextView)view.findViewById(R.id.suggest_item_title7);
        suggest_item_title8 = (TextView)view.findViewById(R.id.suggest_item_title8);

        suggest_item_text1 = (TextView)view.findViewById(R.id.suggest_item_text1);
        suggest_item_text2 = (TextView)view.findViewById(R.id.suggest_item_text2);
        suggest_item_text3 = (TextView)view.findViewById(R.id.suggest_item_text3);
        suggest_item_text4 = (TextView)view.findViewById(R.id.suggest_item_text4);
        suggest_item_text5 = (TextView)view.findViewById(R.id.suggest_item_text5);
        suggest_item_text6 = (TextView)view.findViewById(R.id.suggest_item_text6);
        suggest_item_text7 = (TextView)view.findViewById(R.id.suggest_item_text7);
        suggest_item_text8 = (TextView)view.findViewById(R.id.suggest_item_text8);

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);

        add_position.setTypeface(iconfont);
        tmpTextView.setTypeface(numberTypeface);
    }

    private void initListView() {
        listView.setFocusable(false);
        listItem = new ArrayList<WeatherService.DailyForecastItem>();
        listAdapter = new FutureWeatherListViewAdapter(getActivity(), listItem);
        listView.setAdapter(listAdapter);
    }

    private void setListener() {
        add_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CityManageActivity.class);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
            }
        });
        mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
                //ToastManager.toast(getActivity(), "success!");
            }
        });
    }

    private void configWebView(){
        WebSettings webSettings = webview.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        webview.loadUrl("file:///android_asset/index.html");
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            String id = WeatherService.getCacheWeatherILnfoId(getActivity());
            if(id!=null && !id.equals("")){
                SqlLiteService sqlLiteService = new SqlLiteService(getActivity());
                LocationWeatherEntity  entity = sqlLiteService.getLocationInfoByFormatted(id);
                getWeatherInfo(entity.getGd_city());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here
            // Call onRefreshComplete when the list has been refreshed.
            super.onPostExecute(result);
        }
    }

    public void getWeatherByLocationInfo() {
        PositionService.getPositionInfo(getActivity(), new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        getWeatherInfo(aMapLocation.getCity());
                    }
                }
            }
        });
    }

    private void getWeatherInfoByCache() {
        WeatherService.WeatherInfo weatherInfo = WeatherService.getCacheWeatherInfo(getActivity());
        if(weatherInfo!=null){
            syncWeatherInfo(weatherInfo);
        }else{
            getWeatherByLocationInfo();
        }
    }

    /**
     * 获取温度信息
     */
    public void getWeatherInfo(final String city){
        WeatherService.getWeatherInfo(getActivity(), city, new WeatherService.WeatherCallback() {
            @Override
            public void reject(Exception e) {
                ToastManager.toast(getActivity(), "信息获取失败，请稍后尝试");
                //同步失败后收回下拉菜单
                mPullRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void resolve(final String weatherInfoString) {
                final WeatherService.WeatherInfo weatherInfo = WeatherService.analysisWeatherInfo(weatherInfoString);
                syncWeatherInfo(weatherInfo);
                final SqlLiteService sqlLiteService = new SqlLiteService(getActivity());
                LocationWeatherEntity entity = new LocationWeatherEntity();

                InputTipsService.getInputTipsInfo(getActivity(), weatherInfo.HeWeather5.get(0).basic.city, new InputTipsService.InputTipsCallback() {
                    @Override
                    public void reject(Exception e) {

                    }

                    @Override
                    public void resolve(InputTipsService.InputTipsInfo inputTipsInfo) {
                        InputTipsService.Tips tip = inputTipsInfo.geocodes.get(0);
                        if(weatherInfo.HeWeather5.get(0).status.equals("ok")){
                            LocationWeatherEntity entity = new LocationWeatherEntity();
                            entity.setFormatted_address(tip.formatted_address);
                            entity.setGd_province(tip.province);
                            entity.setGd_city(tip.getCity());
                            entity.setGd_district(tip.getDistrict());
                            entity.setLocation(tip.location);
                            entity.setHf_weather(weatherInfoString);
                            sqlLiteService.saveOrUpdateLocationInfo(entity);
                            WeatherService.cacheWeatherInfoId(getActivity(),tip.formatted_address);
                        }else{
                            ToastManager.toast(getActivity(), "暂无此城市天气数据");
                        }
                    }
                });
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
            listItem.add(item);
        }
        listAdapter.notifyDataSetChanged();


        tmpTextView.setText(heWeather5Item.now.tmp + "°");
        dirTextView.setText(heWeather5Item.now.wind.dir);
        scTextView.setText(heWeather5Item.getNowWindSc() + "级");
        humTextView.setText(heWeather5Item.now.hum + "%");
        weather_qlty.setText("空气质量：" + heWeather5Item.getAqiCityQlty());
        weather_aqi.setText(heWeather5Item.getAqiCityAqi());
        weather_city.setText(heWeather5Item.basic.city + " | ");
        weather_txt_d.setText(heWeather5Item.now.cond.txt);

        String str = heWeather5Item.now.cond.txt;

        if(
                str.equals("小雪")||
                str.equals("中雪")||
                str.equals("大雪")||
                str.equals("暴雪")||
                str.equals("阵雪")
                ){
            webview.loadUrl("javascript:changeWeather(0)");
        }else if(
                str.equals("阵雨")||
                        str.equals("强阵雨")||
                        str.equals("小雨")||
                        str.equals("毛毛雨")||
                        str.equals("细雨")||
                        str.equals("中雨")||
                        str.equals("大雨")||
                        str.equals("暴雨")||
                        str.equals("大暴雨")||
                        str.equals("特大暴雨")
                ){
            webview.loadUrl("javascript:changeWeather(1)");
        }else if(
                str.equals("薄雾")||
                str.equals("雾")||
                str.equals("霾")
                ){
            webview.loadUrl("javascript:changeWeather(2)");
        }else if(
                str.equals("多云")||
                str.equals("晴间多云")||
                str.equals("多云")||
                str.equals("多云")
                ){
            webview.loadUrl("javascript:changeWeather(3)");
        }else if(str.equals("沙尘暴")){
            webview.loadUrl("javascript:changeWeather(4)");
        }else if(str.equals("晴")){
            webview.loadUrl("javascript:changeWeather(5)");
        }else if(
                str.equals("冻雨")||
                str.equals("雨夹雪")||
                str.equals("阵雨夹雪")||
                str.equals("雨雪天气")
                ){
            webview.loadUrl("javascript:changeWeather(6)");
        }else if(
                str.equals("阴")
                ){
            webview.loadUrl("javascript:changeWeather(7)");
        }else if(
                str.equals("雷阵雨")||
                str.equals("强雷阵雨")||
                str.equals("雷阵雨伴有冰雹")
                ){
            webview.loadUrl("javascript:changeWeather(8)");
        }


        suggest_item_title1.setText("概况："+heWeather5Item.suggestion.comf.brf);
        suggest_item_title2.setText("空气："+heWeather5Item.suggestion.air.brf);
        suggest_item_title3.setText("穿衣："+heWeather5Item.suggestion.drsg.brf);
        suggest_item_title4.setText("感冒："+heWeather5Item.suggestion.flu.brf);
        suggest_item_title5.setText("旅游："+heWeather5Item.suggestion.trav.brf);
        suggest_item_title6.setText("紫外线："+heWeather5Item.suggestion.uv.brf);
        suggest_item_title7.setText("运动："+heWeather5Item.suggestion.sport.brf);
        suggest_item_title8.setText("洗车："+heWeather5Item.suggestion.cw.brf);

        suggest_item_text1.setText(heWeather5Item.suggestion.comf.txt);
        suggest_item_text2.setText(heWeather5Item.suggestion.air.txt);
        suggest_item_text3.setText(heWeather5Item.suggestion.drsg.txt);
        suggest_item_text4.setText(heWeather5Item.suggestion.flu.txt);
        suggest_item_text5.setText(heWeather5Item.suggestion.trav.txt);
        suggest_item_text6.setText(heWeather5Item.suggestion.uv.txt);
        suggest_item_text7.setText(heWeather5Item.suggestion.sport.txt);
        suggest_item_text8.setText(heWeather5Item.suggestion.cw.txt);

        //同步完成后收回下拉菜单
        mPullRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void onStart() {
        String id = WeatherService.getCacheWeatherILnfoId(getActivity());
        if(id!=null && !id.equals("")){
            SqlLiteService sqlLiteService = new SqlLiteService(getActivity());
            LocationWeatherEntity  entity = sqlLiteService.getLocationInfoByFormatted(id);
            getWeatherInfo(entity.getGd_city());
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (webview != null) {
            webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webview.clearHistory();

            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.destroy();
            webview = null;
        }
        super.onDestroy();
    }
}
