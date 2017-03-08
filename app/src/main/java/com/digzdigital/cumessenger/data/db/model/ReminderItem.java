package com.digzdigital.cumessenger.data.db.model;


import java.util.Date;

// import io.realm.RealmObject;
// import io.realm.annotations.PrimaryKey;

public class ReminderItem /*extends RealmObject*/ {

    // @PrimaryKey
    private long id;
    private String title;
    private String message;
    private Date date;
    private String venue;
    private String sender;
    private String senderID;

    public ReminderItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
