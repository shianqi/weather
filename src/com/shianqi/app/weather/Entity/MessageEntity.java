package com.shianqi.app.weather.Entity;

/**
 * 聊天实体类
 * Created by admin on 2017/8/10.
 */
public class MessageEntity {
    public static final int TYPE_TEXT = 1001;
    public static final int TYPE_IMG = 1002;

    private int type;
    private int owner;
    private String time;

    public MessageEntity(int type, int owner, String time, boolean comeMessage) {
        this.type = type;
        this.owner = owner;
        this.time = time;
        this.comeMessage = comeMessage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private boolean comeMessage;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public boolean isComeMessage() {
        return comeMessage;
    }


}
