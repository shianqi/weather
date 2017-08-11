package com.shianqi.app.weather.Entity;

/**
 * 文本消息
 * Created by admin on 2017/8/10.
 */
public class TextMessageEntity extends MessageEntity {
    private String text;

    public TextMessageEntity(int type, int owner, String time, boolean comeMessage, String text) {
        super(type, owner, time, comeMessage);
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
