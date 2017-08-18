package com.shianqi.app.weather.Entity;

/**
 * 聊天消息实体类
 * Created by admin on 2017/8/10.
 */
public class MessageEntity {
    //文本消息
    public static final int TYPE_TEXT = 1000;
    //图片消息
    public static final int TYPE_IMG = 1001;
    //文件消息
    public static final int TYPE_FILE = 1002;

    public static final String HAVE_READ = "1";
    public static final String UN_READ = "0";

    private int type;
    private String id;
    private String userEntityId;
    private String chatId;
    private String sendTime;
    private String haveRead;
    private String sendState;

    public MessageEntity(int type, String id, String userEntityId, String chatId, String sendTime) {
        this.type = type;
        this.id = id;
        this.chatId = chatId;
        this.userEntityId = userEntityId;
        this.sendTime = sendTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEntityId() {
        return userEntityId;
    }

    public void setUserEntityId(String userEntityId) {
        this.userEntityId = userEntityId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(String haveRead) {
        this.haveRead = haveRead;
    }

    public String getSendState() {
        return sendState;
    }

    public void setSendState(String sendState) {
        this.sendState = sendState;
    }
}
