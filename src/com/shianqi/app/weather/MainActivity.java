package com.shianqi.app.weather;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.shianqi.app.weather.UI.PageOne;
import com.shianqi.app.weather.UI.PageThree;
import com.shianqi.app.weather.UI.PageTwo;
import com.shianqi.app.weather.Utils.ToastManager;

public class MainActivity extends Activity {
    private Button button1;
    private Button button2;
    private Button button3;

    private PageOne pageOne;
    private PageTwo pageTwo;
    private PageThree pageThree;

    private long EXITTIME = 0;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);

        pageOne=new PageOne();
        pageTwo=new PageTwo();
        pageThree=new PageThree();

        setDefaultFragment();
        addOnClickListener();
    }

    private void setDefaultFragment()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_main1, pageOne);
        transaction.replace(R.id.fragment_main2, pageTwo);
        transaction.replace(R.id.fragment_main3, pageThree);
        transaction.hide(pageTwo);
        transaction.hide(pageThree);
        transaction.commit();
    }

    private void addOnClickListener(){

        button1.setOnClickListener(new View.OnClickListener() {
            /**
             * 设置第一个按钮的绑定事件，当这个按钮被点击时：如果这个Fragment不存在，则创建一个。然后切换到这个Fragment
             * @param v view
             */
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.show(pageOne);
                transaction.hide(pageTwo);
                transaction.hide(pageThree);
                transaction.commit();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            /**
             * 设置第二个按钮的绑定事件，当这个按钮被点击时：如果这个Fragment不存在，则创建一个。然后切换到这个Fragment
             * @param v view
             */
            @Override
            public void onClick(View v) {
                ToastManager.toast(MainActivity.this, "hello~");
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.show(pageTwo);
                transaction.hide(pageOne);
                transaction.hide(pageThree);
                transaction.attach(pageTwo);
                transaction.commit();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            /**
             * 设置第三个按钮的绑定事件，当这个按钮被点击时：如果这个Fragment不存在，则创建一个。然后切换到这个Fragment
             * @param v view
             */
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();

                transaction.show(pageThree);
                transaction.hide(pageTwo);
                transaction.hide(pageOne);
                transaction.attach(pageThree);
                transaction.commit();
            }
        });
    }

    /**
     * 监听返回键
     * @param keyCode 按键
     * @param event 事件
     * @return 按键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-EXITTIME) > 2000){
                ToastManager.toast(getApplicationContext(),"再按一次退出程序");
                EXITTIME = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
