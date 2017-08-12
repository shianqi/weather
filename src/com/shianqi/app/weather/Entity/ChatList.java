package com.shianqi.app.weather.Entity;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2017/8/12.
 */
public class ChatList {
    private static ArrayList<ChatEntity> chatList = new ArrayList<ChatEntity>();

    public static ArrayList getChatList(){
        chatList.clear();
        Log.e("chatList","initMessage");
        chatList.add(0, new ChatEntity("1", "", "安卓二班"));
        chatList.add(0, new ChatEntity("3", "", "农夫三拳有点疼"));
        chatList.add(0, new ChatEntity("11", "", "张欣"));

        addMessage("3", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:23", true, "hello world?"));
        addMessage("3", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:25", false, "hello world!"));
        addMessage("3", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:28", true, "how are you?"));

        addMessage("1", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:32", true, "在吗?"));

        addMessage("11", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:42", true, "歪?"));
        addMessage("11", new TextMessageEntity(MessageEntity.TYPE_TEXT, 1, "下午2:56", true, "啥事儿啊??"));

        return chatList;
    }

    public static ChatEntity getChatEntityByUserId(String userId){
        for(int i=0;i<chatList.size();i++){
            if(chatList.get(i).getUserId().equals(userId)){
                return chatList.get(i);
            }
        }
        return null;
    }

    public static void addMessage(String userId, MessageEntity messageEntity){
        for(int i=0;i<chatList.size();i++){
            if(chatList.get(i).getUserId().equals(userId)){
                chatList.get(i).addMessage(messageEntity);
                chatList.add(0, chatList.remove(i));
                break;
            }
        }
    }
}
