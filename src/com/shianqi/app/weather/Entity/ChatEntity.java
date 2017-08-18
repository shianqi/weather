package com.shianqi.app.weather.Entity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 聊天对象实体
 *
 * Created by admin on 2017/8/11.
 */
public class ChatEntity {
    public ChatEntity() {
        messageList = new ArrayList<MessageEntity>();
        userMap = new HashMap<String, UserEntity>();
    }

    /**
     * 消息列表
     */
    private ArrayList<MessageEntity> messageList;

    /**
     * 用户列表
     */
    private HashMap<String, UserEntity> userMap;

    private String chatId;

    /**
     * 获取用户map
     * @return 用户map
     */
    public HashMap<String, UserEntity> getUserMap() {
        return userMap;
    }

    /**
     * 获取消息列表
     * @return 消息列表
     */
    public ArrayList getMessageList(){
        return messageList;
    }

    public MessageEntity getLastMessageEntity() {
        return messageList.get(messageList.size()-1);
    }

    /**
     * 获取未读消息的数量
     * @return 未读消息的数量
     */
    public int getUnreadMessageSize(){
        int size = 0;
        for(int i=0;i<messageList.size();i++){
            if(messageList.get(i).getHaveRead().equals(MessageEntity.UN_READ)){
                size++;
            }
        }
        return size;
    }

    /**
     * 向消息列表中添加消息
     * @param messageEntity 所添加的消息
     */
    public void addMessage(MessageEntity messageEntity){
        messageList.add(messageEntity);
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
