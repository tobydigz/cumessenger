package com.digzdigital.cumessenger.data.messenger.model;

import java.util.Date;

public class MessageObject {
    private String user;
    private String chatWithUserName;
    private String message;
    private String uid;
    private long date;

    public String getUserName() {
        return user;
    }

    public void setUserName(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUId() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getChatWithUserName() {
        return chatWithUserName;
    }

    public void setChatWithUserName(String chatWithUserName) {
        this.chatWithUserName = chatWithUserName;
    }
}
