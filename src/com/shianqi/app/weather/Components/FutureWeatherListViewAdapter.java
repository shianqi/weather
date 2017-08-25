package com.shianqi.app.weather.Components;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.FutureWeatherHolder;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.WeatherService;

import java.util.List;

/**
 * Created by admin on 2017/8/25.
 */
public class FutureWeatherListViewAdapter extends BaseAdapter {
    private Context context;
    private List<WeatherService.DailyForecastItem> list;
    private LayoutInflater inflater = null;
    private Typeface iconfont;

    public FutureWeatherListViewAdapter(Context context, List<WeatherService.DailyForecastItem> list) {
        this.context = context;
        this.list = list;
        iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
        inflater = LayoutInflater.from(this.context);
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
        FutureWeatherHolder futureWeatherHolder = null;
        if(view==null){
            futureWeatherHolder = new FutureWeatherHolder();
            view = inflater.inflate(R.layout.future_weather_list_item, null);
            futureWeatherHolder.future_weather_day = (TextView)view.findViewById(R.id.future_weather_day);
            futureWeatherHolder.future_weather_icon = (TextView)view.findViewById(R.id.future_weather_icon);
            futureWeatherHolder.future_weather_info = (TextView)view.findViewById(R.id.future_weather_info);
            futureWeatherHolder.future_weather_range = (TextView)view.findViewById(R.id.future_weather_range);

            futureWeatherHolder.future_weather_icon.setTypeface(iconfont);

            view.setTag(futureWeatherHolder);
        }else{
            futureWeatherHolder = (FutureWeatherHolder)view.getTag();
        }
        WeatherService.DailyForecastItem item = list.get(i);
        String[] day = {"今天","明天","后天"};
        int dayIndex = 0;
        futureWeatherHolder.future_weather_day.setText(day[dayIndex++]);
        if(item.cond.txt_d.equals("晴") || item.cond.txt_d.equals("少云")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_qing);
        }else if(item.cond.txt_d.equals("多云") || item.cond.txt_d.equals("晴间多云")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_duoyun);
        }else if(item.cond.txt_d.equals("阴")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_duoyun);
        }else if(item.cond.txt_d.equals("有风") ||
                item.cond.txt_d.equals("平静") ||
                item.cond.txt_d.equals("微风") ||
                item.cond.txt_d.equals("和风") ||
                item.cond.txt_d.equals("清风") ||
                item.cond.txt_d.equals("强风") ||
                item.cond.txt_d.equals("劲风") ||
                item.cond.txt_d.equals("疾风") ||
                item.cond.txt_d.equals("大风") ||
                item.cond.txt_d.equals("烈风") ||
                item.cond.txt_d.equals("风暴") ||
                item.cond.txt_d.equals("狂爆风") ||
                item.cond.txt_d.equals("飓风") ||
                item.cond.txt_d.equals("龙卷风") ||
                item.cond.txt_d.equals("热带风暴")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_feng);
        }else if(item.cond.txt_d.equals("阵雨") || item.cond.txt_d.equals("强阵雨")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_zhenyu);
        }else if(item.cond.txt_d.equals("雷阵雨") || item.cond.txt_d.equals("强雷阵雨")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_leizhenyu);
        }else if(item.cond.txt_d.equals("雷阵雨伴有冰雹")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_binbao);
        }else if(item.cond.txt_d.equals("小雨") || item.cond.txt_d.equals("毛毛雨")|| item.cond.txt_d.equals("细雨")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_xiaoyu);
        }else if(item.cond.txt_d.equals("中雨")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_zhongyu);
        }else if(item.cond.txt_d.equals("大雨") ||
                item.cond.txt_d.equals("暴雨") ||
                item.cond.txt_d.equals("大暴雨") ||
                item.cond.txt_d.equals("特大暴雨")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_dayu);
        }else if(item.cond.txt_d.equals("冻雨") ||
                item.cond.txt_d.equals("雨夹雪") ||
                item.cond.txt_d.equals("阵雨夹雪") ||
                item.cond.txt_d.equals("雨雪天气")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_yujiaxue);
        }else if(item.cond.txt_d.equals("小雪")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_xiaoxue);
        }else if(item.cond.txt_d.equals("中雪")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_zhongxue);
        }else if(item.cond.txt_d.equals("大雪")||item.cond.txt_d.equals("暴雪")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_daxue);
        }else if(item.cond.txt_d.equals("阵雪")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_zhenxue);
        }else if(item.cond.txt_d.equals("薄雾") || item.cond.txt_d.equals("雾")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_wu);
        }else if(item.cond.txt_d.equals("霾")){
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_mai);
        }else{
            futureWeatherHolder.future_weather_icon.setText(R.string.weather_icon_qita);
        }
        futureWeatherHolder.future_weather_info.setText(item.cond.txt_d + " | " + item.wind.sc);
        futureWeatherHolder.future_weather_range.setText(item.tmp.min + "° /" + item.tmp.max+"°");
        return view;
    }
}
