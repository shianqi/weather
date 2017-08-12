package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.shianqi.app.weather.Components.ChatAdapter;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.ChatList;
import com.shianqi.app.weather.R;

import java.util.ArrayList;


public class PageThree extends Fragment {
    private View view;
    private ListView listView;
    private ArrayList<ChatEntity> listItem;
    private ChatAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_three, container, false);

        listView = (ListView) view.findViewById(R.id.chat_list);
        listItem = ChatList.getChatList();

        listAdapter = new ChatAdapter(getActivity(), listItem);
        listView.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        listAdapter.notifyDataSetChanged();
    }
}
