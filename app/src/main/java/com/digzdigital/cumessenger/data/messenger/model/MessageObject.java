package com.digzdigital.cumessenger.data.messenger.model;

public class MessageObject {
    private String user;
    private String message;
    private String uid;

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
}
