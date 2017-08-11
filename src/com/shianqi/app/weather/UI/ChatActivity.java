package com.shianqi.app.weather.UI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import com.shianqi.app.weather.Components.MessageAdapter;
import com.shianqi.app.weather.Entity.MessageEntity;
import com.shianqi.app.weather.Entity.TextMessageEntity;
import com.shianqi.app.weather.R;

import java.util.ArrayList;

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

        listView = (ListView) findViewById(R.id.message_list);
        listItem = new ArrayList<MessageEntity>();

        listAdapter = new MessageAdapter(ChatActivity.this, listItem);

        listView.setAdapter(listAdapter);

        listItem.add(new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "2015", true, "hello world?"));
        listItem.add(new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "2015", false, "hello world!"));
        listItem.add(new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "2015", true, "how are you?"));
        listItem.add(new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "2015", false, "Im fine, and you?"));
        listItem.add(new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "2015", true, "Im fine"));

        listAdapter.notifyDataSetChanged();
    }
}
