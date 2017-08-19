package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.shianqi.app.weather.R;

/**
 * 城市管理界面
 * Created by admin on 2017/8/19.
 */
public class CityManageActivity extends Activity {
    private TextView back;
    private Button add_city_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_manage_activity);
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");


        back = (TextView)findViewById(R.id.city_manage_back);
        add_city_button = (Button)findViewById(R.id.city_manage_add_city_button);

        back.setTypeface(iconfont);
        add_city_button.setTypeface(iconfont);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityManageActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
            }
        });
        add_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        initState();
    }

    private void initState(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
    }
}
