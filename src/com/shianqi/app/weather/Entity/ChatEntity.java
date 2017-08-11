package com.shianqi.app.weather.Entity;

/**
 * 聊天对象实体
 * Created by admin on 2017/8/11.
 */
public class ChatEntity {
    public ChatEntity(int userId, String userImg, String userName, String content, String updateTime, int messageSize) {
        this.userId = userId;
        this.userImg = userImg;
        this.userName = userName;
        this.content = content;
        this.updateTime = updateTime;
        this.messageSize = messageSize;
    }

    private int userId;
    private String userImg;
    private String userName;
    private String content;
    private String updateTime;
    private int messageSize;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
}
