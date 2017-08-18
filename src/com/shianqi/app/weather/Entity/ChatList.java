package com.shianqi.app.weather.Entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 2017/8/12.
 */
public class ChatList {
    private static HashMap<String, ChatEntity> chatMap = new HashMap<String, ChatEntity>();

    public static HashMap<String, ChatEntity> getChatList(){
        chatMap.clear();

        addMessage(new MessageTextEntity(MessageEntity.TYPE_TEXT, "aw1216", "5", "15616", "8.15", "hello"));
        addMessage(new MessageTextEntity(MessageEntity.TYPE_TEXT, "aw1216", "5", "15616", "8.15", "hello"));
        addMessage(new MessageTextEntity(MessageEntity.TYPE_TEXT, "aw1216", "3", "15616", "8.15", "hello"));
        addMessage(new MessageTextEntity(MessageEntity.TYPE_TEXT, "aw1216", "5", "15616", "8.15", "hello"));
        return chatMap;
    }

    public static void addMessage(MessageEntity messageEntity){
        String chatId = messageEntity.getChatId();
        ChatEntity entity = chatMap.get(chatId);
        if(entity!=null){
            entity.addMessage(messageEntity);
        }else{
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.addMessage(messageEntity);
            chatEntity.getUserMap().put(
                    messageEntity.getUserEntityId(),
                    new UserEntity(messageEntity.getUserEntityId(), "张三", "//ssl.gstatic.com/gb/images/v1_e3444bc5.png"));
            chatMap.put(chatId, chatEntity);
        }
    }
}
