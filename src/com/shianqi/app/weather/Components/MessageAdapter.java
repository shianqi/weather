package com.shianqi.app.weather.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.shianqi.app.weather.Entity.MessageEntity;
import com.shianqi.app.weather.Entity.MessageHolder;
import com.shianqi.app.weather.Entity.TextMessageEntity;
import com.shianqi.app.weather.R;

import java.util.List;

/**
 * 聊天窗口 Adapter
 * Created by admin on 2017/8/10.
 */
public class MessageAdapter extends BaseAdapter {
    private final int COME_MSG = 0;
    private final int TO_MSG = 1;

    private Context context = null;
    private List<MessageEntity> messageList = null;
    private LayoutInflater inflater = null;

    public MessageAdapter(Context context, List<MessageEntity> chatList){
        this.context = context;
        this.messageList = chatList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
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
        MessageHolder messageHolder = null;
        if (view == null) {
            messageHolder = new MessageHolder();
            if (messageList.get(position).isComeMessage()) {
                view = inflater.inflate(R.layout.come_message_item, null);
            }else {
                view = inflater.inflate(R.layout.to_message_item, null);
            }
            messageHolder.timeTextView = (TextView) view.findViewById(R.id.chat_message_username);
            messageHolder.contentTextView = (TextView) view.findViewById(R.id.chat_message_text);
            //messageHolder.userImageView = (ImageView) view.findViewById(R.id.iv_user_image);
            view.setTag(messageHolder);
        }else {
            messageHolder = (MessageHolder)view.getTag();
        }

        MessageEntity messageEntity = messageList.get(position);
        if(messageEntity.getType() == MessageEntity.TYPE_TEXT){
            messageHolder.timeTextView.setText(messageList.get(position).getTime());
            messageHolder.contentTextView.setText(((TextMessageEntity) messageEntity).getText());
            //messageHolder.userImageView.setImageResource(chatList.get(position).getUserImage());
        }else if(messageEntity.getType() == MessageEntity.TYPE_IMG){

        }


        return view;
    }

    @Override
    public int getItemViewType(int position) {
        MessageEntity entity = messageList.get(position);
        if (entity.isComeMessage())
        {
            return COME_MSG;
        }else{
            return TO_MSG;
        }
    }
}
