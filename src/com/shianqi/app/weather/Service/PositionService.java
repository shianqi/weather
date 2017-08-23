package com.shianqi.app.weather.Service;

import android.content.Context;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by admin on 2017/8/23.
 */
public class PositionService {
    public static void getPositionInfo(Context context, AMapLocationListener locationListener) {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位回调监听
        mLocationClient.setLocationListener(locationListener);
        //启动定位
        mLocationClient.startLocation();
        //异步获取定位结果
    }
}
