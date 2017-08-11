package com.shianqi.app.weather.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.ChatHolder;
import com.shianqi.app.weather.R;

import java.util.List;

/**
 * 聊天窗口实体
 * Created by admin on 2017/8/11.
 */
public class ChatAdapter extends BaseAdapter{
    private Context context = null;
    private List<ChatEntity> chatList = null;
    private LayoutInflater inflater = null;

    public ChatAdapter(Context context, List<ChatEntity> chatList){
        this.context = context;
        this.chatList = chatList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatHolder chatHolder;
        if(view==null){
            chatHolder = new ChatHolder();
            view = inflater.inflate(R.layout.chat_list_item, null);

            chatHolder.userImgImageView = (ImageView)view.findViewById(R.id.chat_item_user_img);
            chatHolder.messageCountTextView = (TextView)view.findViewById(R.id.chat_item_message_count);
            chatHolder.userNameTextView = (TextView)view.findViewById(R.id.chat_item_user_name);
            chatHolder.contentTextView = (TextView)view.findViewById(R.id.chat_item_message_text);
            chatHolder.updateTimeTextView = (TextView)view.findViewById(R.id.chat_item_update_time);
            view.setTag(chatHolder);
        }else {
            chatHolder = (ChatHolder)view.getTag();
        }

        ChatEntity chatEntity = chatList.get(i);

        chatHolder.userImgImageView.setBackground(context.getResources().getDrawable(R.drawable.ic_launcher));
        chatHolder.messageCountTextView.setText(chatEntity.getMessageSize() + "");
        chatHolder.userNameTextView.setText(chatEntity.getUserName());
        chatHolder.contentTextView.setText(chatEntity.getContent());
        chatHolder.updateTimeTextView.setText(chatEntity.getUpdateTime());
        return view;
    }
}
