package com.digzdigital.cumessenger.data.messenger.model;

/**
 * Created by Digz on 11/03/2017.
 */

public class OngoingMessage {
    private String id;
    private String userName;
    private String chatWithUsername;

    public OngoingMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatWithUsername() {
        return chatWithUsername;
    }

    public void setChatWithUsername(String chatWithUsername) {
        this.chatWithUsername = chatWithUsername;
    }
}
