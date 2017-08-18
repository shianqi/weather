package com.shianqi.app.weather.Entity;

/**
 * 文本消息
 * Created by admin on 2017/8/10.
 */
public class MessageTextEntity extends MessageEntity {
    private String text;

    public MessageTextEntity(int type, String id, String userEntityId, String chatId, String sendTime, String text) {
        super(type, id, userEntityId, chatId, sendTime);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
