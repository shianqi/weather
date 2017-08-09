package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import com.shianqi.app.weather.MainActivity;
import com.shianqi.app.weather.R;

/**
 * Created by admin on 2017/8/8.
 */
public class WelcomeActivity extends Activity {
    private static final long DELAY_TIME = 3000;
    private static final int GOHOME = 1000;
    private Button skipButton;
    private boolean haveSkip = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOHOME:
                    goHome();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        skipButton = (Button)findViewById(R.id.skip_wait);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        handler.sendEmptyMessageDelayed(GOHOME, DELAY_TIME);
    }

    private void goHome(){
        if(!haveSkip){
            haveSkip = true;
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
        }

    }
}
