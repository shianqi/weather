package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.TextChatEntity;
import com.shianqi.app.weather.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PageThree extends Fragment {
    private ListView listView;
    private ArrayList<ChatEntity> listItem;
    private ChatAdapter listAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_three, container, false);

        listView = (ListView) view.findViewById(R.id.message_list);
        listItem = new ArrayList<ChatEntity>();

        listAdapter = new ChatAdapter(getActivity(), listItem);

        listView.setAdapter(listAdapter);

        listItem.add(new TextChatEntity(ChatEntity.TYPE_TEXT, 1, "2015", true, "hello world?"));
        listItem.add(new TextChatEntity(ChatEntity.TYPE_TEXT, 1, "2015", false, "hello world!"));
        listItem.add(new TextChatEntity(ChatEntity.TYPE_TEXT, 1, "2015", true, "how are you?"));
        listItem.add(new TextChatEntity(ChatEntity.TYPE_TEXT, 1, "2015", false, "Im fine, and you?"));
        listItem.add(new TextChatEntity(ChatEntity.TYPE_TEXT, 1, "2015", true, "Im fine"));

        listAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
