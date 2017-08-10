package com.shianqi.app.weather.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.ChatHolder;
import com.shianqi.app.weather.Entity.TextChatEntity;
import com.shianqi.app.weather.R;

import java.util.List;

/**
 * 聊天窗口 Adapter
 * Created by admin on 2017/8/10.
 */
public class ChatAdapter extends BaseAdapter {
    private final int COME_MSG = 0;
    private final int TO_MSG = 1;

    private Context context = null;
    private List<ChatEntity> chatList = null;
    private LayoutInflater inflater = null;

    public ChatAdapter(Context context,List<ChatEntity> chatList){
        this.context = context;
        this.chatList = chatList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ChatHolder chatHolder = null;
        if (view == null) {
            chatHolder = new ChatHolder();
            if (chatList.get(position).isComeMessage()) {
                view = inflater.inflate(R.layout.come_message_item, null);
            }else {
                view = inflater.inflate(R.layout.to_message_item, null);
            }
            chatHolder.timeTextView = (TextView) view.findViewById(R.id.chat_message_username);
            chatHolder.contentTextView = (TextView) view.findViewById(R.id.chat_message_text);
            //chatHolder.userImageView = (ImageView) view.findViewById(R.id.iv_user_image);
            view.setTag(chatHolder);
        }else {
            chatHolder = (ChatHolder)view.getTag();
        }

        ChatEntity chatEntity = chatList.get(position);
        if(chatEntity.getType() == ChatEntity.TYPE_TEXT){
            chatHolder.timeTextView.setText(chatList.get(position).getTime());
            chatHolder.contentTextView.setText(((TextChatEntity)chatEntity).getText());
            //chatHolder.userImageView.setImageResource(chatList.get(position).getUserImage());
        }else if(chatEntity.getType() == ChatEntity.TYPE_IMG){

        }


        return view;
    }

    @Override
    public int getItemViewType(int position) {
        ChatEntity entity = chatList.get(position);
        if (entity.isComeMessage())
        {
            return COME_MSG;
        }else{
            return TO_MSG;
        }
    }
}
