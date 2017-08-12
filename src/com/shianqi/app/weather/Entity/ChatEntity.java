package com.shianqi.app.weather.Entity;

import java.util.ArrayList;

/**
 * 聊天对象实体
 * Created by admin on 2017/8/11.
 */
public class ChatEntity {
    public ChatEntity(String userId, String userImg, String userName) {
        this.userId = userId;
        this.userImg = userImg;
        this.userName = userName;
        this.content = "";
        this.updateTime = "";
        this.messageSize = 0;

        this.messageList = new ArrayList<MessageEntity>();
    }

    private String userId;
    private String userImg;
    private String userName;
    private String content;
    private String updateTime;
    private int messageSize;
    private ArrayList<MessageEntity> messageList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public ArrayList getMessageList(){
        readMessage();
        return messageList;
    }

    public void readMessage(){
        this.messageSize = 0;
    }

    public void addMessage(MessageEntity messageEntity){
        messageList.add(messageEntity);
        if(messageEntity.getType()==MessageEntity.TYPE_TEXT){
            this.content = ((TextMessageEntity)messageEntity).getText();
        }else if(messageEntity.getType()==MessageEntity.TYPE_IMG){
            this.content = "[图片]";
        }
        this.updateTime = messageEntity.getTime();
        this.messageSize++;
    }
}
