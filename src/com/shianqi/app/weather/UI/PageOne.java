package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shianqi.app.weather.R;

public class PageOne extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frist_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
