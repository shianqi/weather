package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import com.shianqi.app.weather.R;
import android.view.ViewGroup.LayoutParams;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.shianqi.app.weather.Utils.ToastManager;


import java.util.ArrayList;
import java.util.HashMap;

public class PageOne extends Fragment {
    private View view;
    private NoScrollListview listView;
    private ArrayList<HashMap<String,Object>> listItem;
    private SimpleAdapter listAdapter;
    private PullToRefreshScrollView mPullRefreshScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);
        listView = (NoScrollListview)view.findViewById(R.id.NoScrollListview);
        listView.setFocusable(false);
        listItem = new ArrayList<HashMap<String, Object>>();

        getDate();

        listAdapter = new SimpleAdapter(getActivity(),listItem,
                R.layout.list_items,
                new String[]{
                        "list_item_date",
                        "list_item_beginning_time",
                        "list_item_time",
                        "list_item_score"
                },
                new int[]{
                        R.id.list_item_date,
                        R.id.list_item_beginning_time,
                        R.id.list_item_time,
                        R.id.list_item_score
                });

        listView.setAdapter(listAdapter);

        //setListViewHeight();

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
                //ToastManager.toast(getActivity(), "success!");
            }
        });

        return view;
    }

    private void getDate(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list_item_date", "1");
        map.put("list_item_beginning_time", "2");
        map.put("list_item_time", "3");
        map.put("list_item_score", "4");
        listItem.add(map);
        listItem.add(map);
        listItem.add(map);
    }

    /**
     * 动态设置 ListView 高度，使其可以撑开父元素
     */
    private void setListViewHeight(){
        if (listAdapter == null) {
            return;
        }

        int listViewHeight = 0;
        for(int i=0; i<listAdapter.getCount(); i++){
            View temp = listAdapter.getView(i,null, listView);
            temp.measure(0,0);
            listViewHeight += temp.getMeasuredHeight();
        }

        LayoutParams layoutParams = this.listView.getLayoutParams();
        layoutParams.height = listViewHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.setFocusable(false);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            // Do some stuff here

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshScrollView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        ToastManager.toast(getActivity(), "??");
    }
}
