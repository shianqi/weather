package com.shianqi.app.weather.Entity;

/**
 * Created by admin on 2017/8/22.
 */
public class LocationWeatherEntity {
    public static final String IS_LOCATION = "1001";
    public static final String NOT_LOCATION = "1000";

    //高德地图城市代码
    private String gd_adcode;
    //高德地图名字
    private String gd_name;
    //高德地图描述
    private String gd_district;
    //和风天气API JSON
    private String hf_weather;
    //是否是当前定位城市
    private String isLocation;
    //天气更新时间
    private String updateTime;

    public String getGd_adcode() {
        return gd_adcode;
    }

    public void setGd_adcode(String gd_adcode) {
        this.gd_adcode = gd_adcode;
    }

    public String getGd_name() {
        return gd_name;
    }

    public void setGd_name(String gd_name) {
        this.gd_name = gd_name;
    }

    public String getGd_district() {
        return gd_district;
    }

    public void setGd_district(String gd_district) {
        this.gd_district = gd_district;
    }

    public String getHf_weather() {
        return hf_weather;
    }

    public void setHf_weather(String hf_weather) {
        this.hf_weather = hf_weather;
    }

    public String getIsLocation() {
        return isLocation;
    }

    public void setIsLocation(String isLocation) {
        this.isLocation = isLocation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
