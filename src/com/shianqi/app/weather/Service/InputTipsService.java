package com.shianqi.app.weather.Service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shianqi.app.weather.Utils.ToastManager;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 输入提示服务类
 * Created by admin on 2017/8/21.
 */
public class InputTipsService {
    private static OkHttpClient client = new OkHttpClient.Builder().build();
    private static final String url = "http://restapi.amap.com/v3/geocode/geo";
    private static final String key = "f680d43f581acb4ab72f91a10984e4c1";

    public static interface InputTipsCallback{
        public void reject(Exception e);
        public void resolve(InputTipsInfo inputTipsInfo);
    }

    public static class InputTipsInfo {
        public String status;
        public String count;
        public String info;
        public String infocode;
        public List<Tips> geocodes;
    }

    public static class Tips {
        public String formatted_address;
        public String province;
        public String citycode;
        public Object city;
        public Object district;
        public String location;

        public String getDistrict(){
            if(district!=null && !district.toString().equals("[]")){
                return district.toString();
            }else{
                return getCity();
            }
        }

        public String getCity(){
            if(city!=null && !city.toString().equals("[]")){
                return city.toString();
            }else{
                return province;
            }
        }
    }


    public static InputTipsInfo analysisInputTipsInfo(String inputTipsInfoStr){
        Gson gson = new Gson();
        Type type = new TypeToken<InputTipsInfo>() {}.getType();
        return gson.fromJson(inputTipsInfoStr, type);
    }

    public static void getInputTipsInfo(final Context context, final String address, final InputTipsCallback inputTipsCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url+"?key="+key+"&address="+address).build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                inputTipsCallback.reject(e);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String body = response.body().string();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                InputTipsInfo info = analysisInputTipsInfo(body);
                                if(info.status.equals("0")){
                                    inputTipsCallback.resolve(null);
                                }else{
                                    inputTipsCallback.resolve(info);
                                }

                            }
                        });
                    }
                });
            }
        }).start();
    }

}
