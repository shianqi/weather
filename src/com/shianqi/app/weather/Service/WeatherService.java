package com.shianqi.app.weather.Service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shianqi.app.weather.Utils.SharedPreferencesManager;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 天气服务
 * 采用和风天气 API
 * Created by shianqi@imudges.com on 2017/8/9.
 */
public class WeatherService {
    //和风天气秘钥
    private final static String key = "9df11e1434f44ac5ab00bb50128772e2";
    //全部天气
    private final static String url = "https://free-api.heweather.com/v5/weather";

    private static SharedPreferencesManager manager;

    /**
     * 获取天气信息回调函数
     */
    public static interface WeatherCallback{
        public void reject(Exception e);
        public void resolve(String weatherInfoString);
    }

    /**
     * 天气信息的JavaBean
     * HeWeather5	和风标识
     * status	状态码
     * daily_forecast	天气预报
     * deg	风向（360度）
     * sc	风力等级
     * spd	风速
     * dir	风向
     * pres	气压
     * vis	能见度
     * wind	风力情况
     * astro	天文指数
     * mr	月升时间
     * sr	日出时间
     * ss	日落时间
     * ms	月落时间
     * cond	天气状况
     * code_n	夜间天气状况代码
     * code_d	白天天气状况代码
     * txt_n	夜间天气状况描述
     * txt_d	白天天气状况描述
     * tmp	温度
     * max	最高温度
     * min	最低温度
     * pop	降水概率
     * date	日期
     * pcpn	降水量
     * hum	相对湿度
     * aqi	AQI
     * city	城市名
     * no2	NO2
     * pm10	PM10
     * o3	O3
     * qlty	空气质量
     * pm25	PM2.5
     * so2	SO2
     * co	CO
     * hourly_forecast	每小时预报
     * code	天气状况代码
     * txt	数据详情
     * alarms	灾害预警
     * type	预警种类
     * stat	预警状态
     * title	预警标题
     * level	预警级别
     * suggestion	生活指数
     * cw	洗车指数
     * brf	简介
     * drsg	穿衣指数
     * comf	舒适度指数
     * uv	紫外线指数
     * flu	感冒指数
     * trav	旅游指数
     * sport	运动指数
     * basic	基本信息
     * lat	纬度
     * cnty	国家
     * update	更新时间
     * utc	UTC时间
     * loc	当地时间
     * id	城市ID
     * lon	经度
     * now	实况天气
     * fl	体感温度
     */
    public static class WeatherInfo{
        public List<HeWeather5Item> HeWeather5;
    }

    public static class HeWeather5Item{
        public Aqi aqi;
        public Basic basic;
        public List<DailyForecastItem> daily_forecast;
        public List<HourlyForecastItem> hourly_forecast;
        public Now now;
        public String status;
        public Suggestion suggestion;

        public String getAqiCityQlty() {
            if(aqi!=null && aqi.city!=null && aqi.city.qlty!=null){
                return aqi.city.qlty;
            }else{
                return "-";
            }
        }

        public String getAqiCityAqi() {
            if(aqi!=null && aqi.city!=null && aqi.city.aqi!=null){
                return aqi.city.aqi;
            }else{
                return "-";
            }
        }
    }

    public static class Aqi{
        public AqiCity city;
    }

    public static class AqiCity{
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        public String qlty;
        public String so2;
    }

    public static class Basic{
        public String city;
        public String cnty;
        public String id;
        public String lat;
        public String lon;
        public BasicUpdate update;
    }

    public static class BasicUpdate{
        public String loc;
        public String utc;
    }

    public static class DailyForecastItem{
        public DailyForecastAstro astro;
        public DailyForecastCond cond;
        public String date;
        public String hum;
        public String pcpn;
        public String pop;
        public String pres;
        public DailyForecastTmp tmp;
        public String uv;
        public String vis;
        public Wind wind;
    }

    public static class DailyForecastAstro{
        public String mr;
        public String ms;
        public String sr;
        public String ss;
    }

    public static class DailyForecastCond{
        public String code_d;
        public String code_n;
        public String txt_d;
        public String txt_n;
    }

    public static class DailyForecastTmp{
        public String max;
        public String min;
    }

    public static class Wind{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }

    public static class HourlyForecastItem{
        public HourlyForecastCond cond;
        public String date;
        public String hum;
        public String pop;
        public String pres;
        public String tmp;
        public Wind wind;
    }

    public static class HourlyForecastCond{
        public String code;
        public String txt;
    }

    public static class Now{
        public NowCond cond;
        public String fl;
        public String hum;
        public String pcpn;
        public String pres;
        public String tmp;
        public String vis;
        public Wind wind;
    }

    public static class NowCond{
        public String code;
        public String txt;
    }

    public static class Suggestion{
        public SuggestionItem air;
        public SuggestionItem comf;
        public SuggestionItem cw;
        public SuggestionItem drsg;
        public SuggestionItem flu;
        public SuggestionItem sport;
        public SuggestionItem trav;
        public SuggestionItem uv;
    }

    public static class SuggestionItem{
        public String brf;
        public String txt;
    }

    private static OkHttpClient client = new OkHttpClient.Builder().build();

    /**
     * 获取天气信息
     * @param city 城市
     * @param weatherCallback 回调
     */
    public static void getWeatherInfo(final Context context, final String city, final WeatherCallback weatherCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url+"?city="+city+"&key="+key).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                weatherCallback.reject(e);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        cacheWeatherInfo(context, body);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                weatherCallback.resolve(body);
                            }
                        });
                    }
                });
            }
        }).start();
    }

    /**
     * 将天气信息json解析为对象
     * @param weatherInfoStr 天气信息（json）
     * @return 天气对象
     */
    public static WeatherInfo analysisWeatherInfo(String weatherInfoStr){
        Gson gson = new Gson();
        Type type = new TypeToken<WeatherInfo>() {}.getType();
        return gson.fromJson(weatherInfoStr, type);
    }

    /**
     * 将天气信息写入缓存
     * @param weatherInfoStr 天气信息（json字符串）
     */
    public static void cacheWeatherInfo(Context context, String weatherInfoStr){
        if(manager == null){
            manager = new SharedPreferencesManager(context);
        }
        manager.writeString("weather", weatherInfoStr);
    }

    /**
     * 从缓存中提取天气信息
     * @return 天气信息
     */
    public static WeatherInfo getCacheWeatherInfo(Context context){
        if(manager == null){
            manager = new SharedPreferencesManager(context);
        }
        String weatherInfoStr = manager.readString("weather");
        return analysisWeatherInfo(weatherInfoStr);
    }
}
