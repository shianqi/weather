package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.shianqi.app.weather.Components.MessageAdapter;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.ChatList;
import com.shianqi.app.weather.Entity.MessageEntity;
import com.shianqi.app.weather.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 聊天界面
 * Created by admin on 2017/8/11.
 */
public class ChatActivity extends Activity{
    private ListView listView;
    private ArrayList<MessageEntity> listItem;
    private MessageAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_page);

        Bundle bundle = this.getIntent().getExtras();

        /*获取Bundle中的数据，注意类型和key*/
        String chatId = bundle.getString("ChatID");

        listView = (ListView) findViewById(R.id.message_list);

        HashMap chatMap = ChatList.getChatList();

        listItem = ((ChatEntity)chatMap.get(chatId)).getMessageList();

        listAdapter = new MessageAdapter(ChatActivity.this, listItem, ((ChatEntity)chatMap.get(chatId)).getUserMap());

        listView.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged();
    }
}
