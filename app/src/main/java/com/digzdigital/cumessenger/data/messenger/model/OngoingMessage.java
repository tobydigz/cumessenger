package com.digzdigital.cumessenger.data.messenger.model;

/**
 * Created by Digz on 11/03/2017.
 */

public class OngoingMessage {
    private String id;
    private String userId;
    private String chatWithUserId;
    private String userName;
    private String chatWithUserName;


    public OngoingMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatWithUserId() {
        return chatWithUserId;
    }

    public void setChatWithUserId(String chatWithUserId) {
        this.chatWithUserId = chatWithUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatWithUserName() {
        return chatWithUserName;
    }

    public void setChatWithUserName(String chatWithUserName) {
        this.chatWithUserName = chatWithUserName;
    }
}
