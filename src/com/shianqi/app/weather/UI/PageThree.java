package com.shianqi.app.weather.UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.shianqi.app.weather.Components.ChatAdapter;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.R;

import java.util.ArrayList;
import java.util.List;


public class PageThree extends Fragment {
    private View view;
    private Button button;
    private ListView listView;
    private List<ChatEntity> listItem;
    private ChatAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_three, container, false);



        button = (Button) view.findViewById(R.id.chat_to);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                getActivity().startActivity(intent);
            }
        });


        listView = (ListView) view.findViewById(R.id.chat_list);
        listItem = new ArrayList<ChatEntity>();

        listAdapter = new ChatAdapter(getActivity(), listItem);
        listView.setAdapter(listAdapter);

        listItem.add(new ChatEntity(1, "", "安卓二班", "小伙伴们和建伟老师辛苦了~", "下午2:23", 3));
        listItem.add(new ChatEntity(1, "", "农夫三拳有点疼", "??", "下午2:23", 3));
        listItem.add(new ChatEntity(1, "", "张欣", "你领取了张欣的红包", "下午2:23", 3));

        listAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
