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
                "gd_adcode varchar(20)," +
                "gd_name varchar(100)," +
                "gd_district varchar(100)," +
                "hf_weather varchar(3000)," +
                "isLocation varchar(10)," +
                "updateTime varchar(20))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void saveOrUpdateLocationInfo(LocationWeatherEntity entity){
        if(entity==null){
            return;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("gd_adcode", entity.getGd_adcode());
        cv.put("gd_name", entity.getGd_name());
        cv.put("gd_district", entity.getGd_district());
        cv.put("hf_weather", entity.getHf_weather());
        cv.put("isLocation", entity.getIsLocation());
        cv.put("updateTime", System.currentTimeMillis()+"");

        String sql = "SELECT * FROM LocationInfo WHERE gd_adcode = ?;" ;
        Cursor cursor = db.rawQuery(sql,new String[]{entity.getGd_adcode()});
        if (cursor.getCount() == 0){
            //插入
            db.insert("LocationInfo",null,cv);
        }else {
            db.update("LocationInfo",cv,"gd_adcode = ?",new String[]{entity.getGd_adcode()});
        }
        db.close();
    }

    public List getAllLocationInfo() {
        List<LocationWeatherEntity> list = new ArrayList<LocationWeatherEntity>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM LocationInfo;";
        Cursor cursor = db.rawQuery(sql,new String[]{});
        while (cursor.moveToNext()){
            LocationWeatherEntity entity = new LocationWeatherEntity();
            entity.setGd_adcode(cursor.getString(cursor.getColumnIndex("gd_adcode")));
            entity.setGd_district(cursor.getString(cursor.getColumnIndex("gd_district")));
            entity.setGd_district(cursor.getString(cursor.getColumnIndex("gd_district")));
            entity.setHf_weather(cursor.getString(cursor.getColumnIndex("hf_weather")));
            entity.setIsLocation(cursor.getString(cursor.getColumnIndex("isLocation")));
            entity.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
            list.add(entity);
        }
        return list;
    }
}
