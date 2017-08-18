package com.shianqi.app.weather.Entity;

/**
 * Created by admin on 2017/8/17.
 */
public class UserEntity {
    public UserEntity(String userId, String username, String userImg) {
        this.userId = userId;
        this.username = username;
        this.userImg = userImg;
    }

    private String userId;
    private String username;
    private String userImg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

}
