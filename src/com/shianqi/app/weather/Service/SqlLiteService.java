package com.shianqi.app.weather.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.shianqi.app.weather.Entity.LocationWeatherEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/8/22.
 */
public class SqlLiteService extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "hl.db";
    private static final int DATABASE_VERSION = 1;

    public SqlLiteService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE LocationInfo (" +
                "formatted_address varchar(100)," +
                "gd_province varchar(20)," +
                "gd_city varchar(20)," +
                "gd_district varchar(20)," +
                "location varchar(100)," +
                "hf_weather varchar(3000)," +
                "updateTime varchar(20))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void removeLocationInfo(String getFormatted_address){
        if (getFormatted_address==null || getFormatted_address.equals("")){
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM LocationInfo WHERE formatted_address = ?;" ;
        db.execSQL(sql,new String[]{ getFormatted_address });
    }

    public void saveOrUpdateLocationInfo(LocationWeatherEntity entity){
        if(entity==null){
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("formatted_address", entity.getFormatted_address());
        cv.put("gd_province", entity.getGd_province());
        cv.put("gd_city", entity.getGd_city());
        cv.put("gd_district", entity.getGd_district().toString().equals("[]")?"":entity.getGd_district().toString());
        cv.put("location", entity.getLocation());
        cv.put("hf_weather", entity.getHf_weather());
        cv.put("updateTime", System.currentTimeMillis()+"");

        String sql = "SELECT * FROM LocationInfo WHERE formatted_address = ?;" ;
        Cursor cursor = db.rawQuery(sql,new String[]{entity.getFormatted_address()});
        if (cursor.getCount() == 0){
            //插入
            db.insert("LocationInfo",null,cv);
        }else {
            db.update("LocationInfo",cv,"formatted_address = ?",new String[]{entity.getFormatted_address()});
        }
    }

    public List getAllLocationInfo() {
        List<LocationWeatherEntity> list = new ArrayList<LocationWeatherEntity>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM LocationInfo;";
        Cursor cursor = db.rawQuery(sql,new String[]{});
        while (cursor.moveToNext()){
            LocationWeatherEntity entity = new LocationWeatherEntity();
            entity.setFormatted_address(cursor.getString(cursor.getColumnIndex("formatted_address")));
            entity.setGd_province(cursor.getString(cursor.getColumnIndex("gd_province")));
            entity.setGd_city(cursor.getString(cursor.getColumnIndex("gd_city")));
            entity.setGd_district(cursor.getString(cursor.getColumnIndex("gd_district")));
            entity.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            entity.setHf_weather(cursor.getString(cursor.getColumnIndex("hf_weather")));
            entity.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
            list.add(entity);
        }
        return list;
    }

    public LocationWeatherEntity getLocationInfoByFormatted(String formatted_address) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM LocationInfo WHERE formatted_address = ?;" ;
        Cursor cursor = db.rawQuery(sql,new String[]{formatted_address});
        if(cursor.moveToNext()){
            LocationWeatherEntity entity = new LocationWeatherEntity();
            entity.setFormatted_address(cursor.getString(cursor.getColumnIndex("formatted_address")));
            entity.setGd_province(cursor.getString(cursor.getColumnIndex("gd_province")));
            entity.setGd_city(cursor.getString(cursor.getColumnIndex("gd_city")));
            entity.setGd_district(cursor.getString(cursor.getColumnIndex("gd_district")));
            entity.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            entity.setHf_weather(cursor.getString(cursor.getColumnIndex("hf_weather")));
            entity.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
            return entity;
        }
        return null;
    }
}
