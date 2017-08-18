package com.shianqi.app.weather.Components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.MessageEntity;
import com.shianqi.app.weather.Entity.MessageHolder;
import com.shianqi.app.weather.Entity.MessageTextEntity;
import com.shianqi.app.weather.Entity.UserEntity;
import com.shianqi.app.weather.R;
import com.shianqi.app.weather.Service.UserInfoService;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 聊天窗口 Adapter
 * Created by admin on 2017/8/10.
 */
public class MessageAdapter extends BaseAdapter {
    private static final int COME_MSG = 1000;
    private static final int TO_MSG = 1001;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

    private Context context = null;
    private List<MessageEntity> messageList = null;
    private HashMap<String, UserEntity> userMap = null;
    private LayoutInflater inflater = null;

    public MessageAdapter(Context context, List<MessageEntity> chatList, HashMap<String, UserEntity> userEntityMap){
        this.context = context;
        this.messageList = chatList;
        this.userMap = userEntityMap;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        Log.e("messageSize", ""+messageList.size());
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.e("getView ", + position + " " + view);
        MessageHolder messageHolder = null;
        int type = getItemViewType(position);

        if (view == null) {
            messageHolder = new MessageHolder();
            switch (type) {
                case COME_MSG:
                    view = inflater.inflate(R.layout.come_message_item, null);
                    break;
                case TO_MSG:
                    view = inflater.inflate(R.layout.to_message_item, null);
                    break;
            }
            messageHolder.timeTextView = (TextView) view.findViewById(R.id.chat_message_username);
            messageHolder.contentTextView = (TextView) view.findViewById(R.id.chat_message_text);
            messageHolder.userImageView = (ImageView) view.findViewById(R.id.chat_message_img);
            view.setTag(messageHolder);
        }else {
            messageHolder = (MessageHolder) view.getTag();
        }

        MessageEntity messageEntity = messageList.get(position);
        if(messageEntity.getType() == MessageEntity.TYPE_TEXT){
            messageHolder.timeTextView.setText(messageEntity.getSendTime());
            messageHolder.contentTextView.setText(((MessageTextEntity) messageEntity).getText());

            Picasso.with(context)
                    .load(userMap.get(messageEntity.getUserEntityId()).getUserImg())
                    .into(messageHolder.userImageView);
        }else if(messageEntity.getType() == MessageEntity.TYPE_IMG){

        }
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        String myUserId = UserInfoService.getUserId();
        if (messageList.get(position).getUserEntityId().equals(myUserId))
        {
            return COME_MSG;
        }else{
            return TO_MSG;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }
}
