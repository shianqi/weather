package com.shianqi.app.weather.Entity;

import android.util.Log;

/**
 * Created by admin on 2017/8/22.
 */
public class LocationWeatherEntity {

    //格式化的地点名
    private String formatted_address;
    //高德地图省份
    private String gd_province;
    //高德地图城市
    private String gd_city;
    //高德地图区
    private String gd_district;
    //高德地图坐标点
    private String location;
    //和风天气API JSON
    private String hf_weather;

    //天气更新时间
    private String updateTime;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getGd_province() {
        return gd_province;
    }

    public void setGd_province(String gd_province) {
        this.gd_province = gd_province;
    }

    public String getGd_city() {
        if(gd_city != null && !gd_city.equals("[]") && !gd_city.equals("") ){
            return gd_city;
        }else{
            return getGd_province();
        }
    }

    public void setGd_city(String gd_city) {
        this.gd_city = gd_city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGd_district() {
        if(gd_district != null && !gd_district.equals("[]") && !gd_district.equals("") ){
            return gd_district;
        }else{
            return getGd_city();
        }
    }

    public String getOtherLocation() {
        boolean state = true;
        String res = "";
        if(gd_district != null && !gd_district.equals("[]") && !gd_district.equals("") ){
            Log.e("gd_district", gd_district);
            if(state){
                state = false;
            }else{
                res += gd_district;
            }
        }
        if(gd_city != null && !gd_city.equals("[]") && !gd_city.equals("") ){
            if(state){
                state = false;
            }else{
                res += gd_city + "，";
            }
        }
        if(gd_province != null && !gd_province.equals("[]") && !gd_province.equals("") ){
            if(state){
                state = false;
            }else{
                res += gd_province;
            }
        }
        return res;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
