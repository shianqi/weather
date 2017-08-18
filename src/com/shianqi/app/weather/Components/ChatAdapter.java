package com.shianqi.app.weather.Components;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.ChatEntity;
import com.shianqi.app.weather.Entity.ChatHolder;
import com.shianqi.app.weather.Entity.MessageEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.UI.ChatActivity;

import java.util.ArrayList;

/**
 * 聊天窗口实体
 * Created by admin on 2017/8/11.
 */
public class ChatAdapter extends BaseAdapter{
    private Context context = null;
    private ArrayList<ChatEntity> chatList = null;
    private LayoutInflater inflater = null;

    public ChatAdapter(Context context, ArrayList<ChatEntity> chatList){
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

            final int position = i;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("ChatID", chatList.get(position).getChatId());
                    context.startActivity(intent);
                }
            });

            view.setTag(chatHolder);
        }else {
            chatHolder = (ChatHolder)view.getTag();
        }

        ChatEntity chatEntity = chatList.get(i);

        chatHolder.userImgImageView.setBackground(context.getResources().getDrawable(R.drawable.ic_launcher));
        int unReadMessageSize = chatEntity.getUnreadMessageSize();
        chatHolder.messageCountTextView.setText(unReadMessageSize>99 ? "99+": unReadMessageSize + "");
        if(unReadMessageSize<=0){
            chatHolder.messageCountTextView.setVisibility(View.INVISIBLE);
        }

        MessageEntity lastMessageEntity = chatEntity.getLastMessageEntity();
        String userEntityId = lastMessageEntity.getUserEntityId();

        chatHolder.userNameTextView.setText(chatEntity.getUserMap().get(userEntityId).getUsername());
        chatHolder.contentTextView.setText(chatEntity.getUnreadMessageSize());
        chatHolder.updateTimeTextView.setText(lastMessageEntity.getSendTime());
        return view;
    }


}
