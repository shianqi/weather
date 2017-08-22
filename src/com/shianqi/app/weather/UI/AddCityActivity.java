package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shianqi.app.weather.Components.AddCityListViewAdapter;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.InputTipsService;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加位置页面
 * Created by admin on 2017/8/21.
 */
public class AddCityActivity extends Activity {
    private EditText editText;
    private List listItem;
    private ListView listView;
    private AddCityListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city_activity);

        listView = (ListView)findViewById(R.id.add_city_list_view);
        listItem = new ArrayList<InputTipsService.Tips>();
        adapter = new AddCityListViewAdapter(AddCityActivity.this, listItem);
        listView.setAdapter(adapter);

        editText = (EditText)findViewById(R.id.add_city_input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String str = editable.toString();
                InputTipsService.getInputTipsInfo(AddCityActivity.this, str, new InputTipsService.InputTipsCallback() {
                    @Override
                    public void reject(Exception e) {
                        ToastManager.toast(AddCityActivity.this, "搜索失败，请检查网络连接");
                    }

                    @Override
                    public void resolve(InputTipsService.InputTipsInfo inputTipsInfo) {
                        listItem.clear();
                        for(int i=0;i<inputTipsInfo.tips.size();i++){
                            listItem.add(inputTipsInfo.tips.get(i));
                        }
                        ToastManager.toast(AddCityActivity.this, listItem.size()+"");
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        getPosition();
        initState();
    }

    private void getPosition() {
        //声明AMapLocationClient类对象
        AMapLocationClient mLocationClient = null;
        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        //声明定位回调监听器
        AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        Log.e("定位成功", aMapLocation.getAddress());
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        mLocationClient.startLocation();
        //异步获取定位结果
    }

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            AddCityActivity.this.finish();
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
        }
        return super.onKeyDown(keyCode, event);
    }
}
