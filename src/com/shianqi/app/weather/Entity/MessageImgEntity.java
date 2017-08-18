package com.shianqi.app.weather.Entity;

/**
 * 图片消息
 * Created by admin on 2017/8/11.
 */
public class MessageImgEntity extends MessageEntity {
    private String imgUrl;

    public MessageImgEntity(int type, String id, String userEntityId, String chatId, String sendTime, String imgUrl) {
        super(type, id, userEntityId, chatId, sendTime);
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
