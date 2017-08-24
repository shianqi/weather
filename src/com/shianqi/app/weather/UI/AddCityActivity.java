package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.shianqi.app.weather.Components.AddCityListViewAdapter;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.InputTipsService;
import com.shianqi.app.weather.Service.PositionService;
import com.shianqi.app.weather.Service.SqlLiteService;
import com.shianqi.app.weather.Utils.ToastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private TextView backTextView;
    private TextView tipsTextView;
    private TextView cleanTextView;
    private AddCityListViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city_activity);

        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");

        findView();
        setListener();


        backTextView.setTypeface(iconfont);
        cleanTextView.setTypeface(iconfont);

        listItem = new ArrayList<InputTipsService.Tips>();
        adapter = new AddCityListViewAdapter(AddCityActivity.this, listItem, new AddCityListViewAdapter.Callback() {
            @Override
            public void close() {
                exit();
            }
        });
        listView.setAdapter(adapter);

        addRecommendCity();
        getPosition();
        initState();
    }

    private void getPosition() {
        PositionService.getPositionInfo(getApplicationContext(), new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        Log.e("定位成功", aMapLocation.getAddress());
                    }
                }
            }
        });
    }

    private void findView() {
        listView = (ListView)findViewById(R.id.add_city_list_view);
        editText = (EditText)findViewById(R.id.add_city_input);
        backTextView = (TextView)findViewById(R.id.add_city_back);
        cleanTextView = (TextView)findViewById(R.id.add_city_clean);
        tipsTextView = (TextView)findViewById(R.id.add_city_tips);
    }

    private void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });
        cleanTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
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
                        if(inputTipsInfo==null || inputTipsInfo.count.equals("0")){
                            tipsTextView.setText("无匹配城市");
                            tipsTextView.setVisibility(View.VISIBLE);
                            return;
                        }else{
                            tipsTextView.setVisibility(View.INVISIBLE);
                        }
                        for(int i=0;i<inputTipsInfo.geocodes.size();i++){
                            listItem.add(inputTipsInfo.geocodes.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                //如果字符串为空，那么显示推荐消息
                if(str.equals("")){
                    cleanTextView.setVisibility(View.INVISIBLE);
                    addRecommendCity();
                }else{
                    cleanTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public String getJson(String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void addRecommendCity(){
        String json = getJson("json/city.json");
        listItem.clear();
        InputTipsService.InputTipsInfo inputTipsInfo = InputTipsService.analysisInputTipsInfo(json);
        for(int i=0;i<inputTipsInfo.geocodes.size();i++){
            listItem.add(inputTipsInfo.geocodes.get(i));
        }
        adapter.notifyDataSetChanged();
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
            exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        AddCityActivity.this.finish();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }
}
