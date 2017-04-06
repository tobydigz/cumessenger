package com.digzdigital.cumessenger.data.messenger;

import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class AppApiHelper implements ApiHelper {

    private DatabaseReference databaseReference;
    private ArrayList<MessageObject> messages = new ArrayList<>();
    private MessageObject message;
    private ArrayList<MessageObject> forumMessages;
    private ArrayList<Forum> fora;
    private String username, chatWithUsername;
    private int messageCounter = 1;
    private ArrayList<OngoingMessage> ongoingMessages = new ArrayList<>();

    public AppApiHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void setChatUsers(String username, String chatWithUsername) {
        this.username = username;
        this.chatWithUsername = chatWithUsername;
    }



    @Override
    public void SendMessage(MessageObject messageObject) {
        String key = databaseReference.child("messages").child(username).child(chatWithUsername).push().getKey();
        databaseReference.child("messages").child(username).child(chatWithUsername).child(key).setValue(messageObject);
        databaseReference.child("messages").child(username).child(chatWithUsername).child("email").setValue(messageObject.getChatWithUserName());
        databaseReference.child("messages").child(chatWithUsername).child(username).child(key).setValue(messageObject);
        databaseReference.child("messages").child(chatWithUsername).child(username).child("email").setValue(messageObject.getUserName());
    }

    @Override
    public void queryForMessages(String userid, String chatWithUserid) {
        databaseReference.child("messages").child(userid).child(chatWithUserid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageObject message = snapshot.getValue(MessageObject.class);
                    messages.add(message);
                }
                if (messageCounter == 1) {
                    postEvent(EventType.MESSAGES);
                    messageCounter++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public void queryForNewMessage() {
        databaseReference.child("messages").child(username).child(chatWithUsername).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                message = dataSnapshot.getValue(MessageObject.class);
                postEvent(EventType.NEW_MESSAGE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public ArrayList<MessageObject> getMessages() {
        return messages;
    }

    @Override
    public MessageObject getNewMessage() {
        return message;
    }

    @Override
    public void queryForForums() {
        databaseReference.child("forums").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fora = null;
                fora = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Forum forum = snapshot.getValue(Forum.class);
                    fora.add(forum);
                }
                postEvent(EventType.FORUMS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<Forum> getFora() {
        return fora;
    }


    @Override
    public void queryForForumMessages(String forumName) {
        databaseReference.child("forums").child(forumName).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                forumMessages = null;
                forumMessages = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageObject messageObject = new MessageObject();
                    messageObject.setMessage((String)snapshot.child("message").getValue());
                    messageObject.setUserName((String)snapshot.child("userName").getValue());
                    messageObject.setUid((String)snapshot.child("uid").getValue());

                    forumMessages.add(messageObject);
                }
                postEvent(EventType.FORUM_MESSAGES);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<MessageObject> getForumMessages() {
        return forumMessages;
    }

    @Override
    public void postForumMessage(MessageObject messageObject, String forumName) {
        databaseReference.child("forums").child(forumName).child("messages").push().setValue(messageObject);
    }

    @Override
    public void queryForOngoingMessages(final String userid) {
        databaseReference.child("messages").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OngoingMessage ongoingMessage = new OngoingMessage();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ongoingMessage.setUserId(userid);
                    ongoingMessage.setUserName((String)dataSnapshot.child("email").getValue());
                    ongoingMessage.setChatWithUserId(snapshot.getKey());
                    ongoingMessage.setChatWithUserName((String)snapshot.child("email").getValue());
                    ongoingMessages.add(ongoingMessage);
                }
                postEvent(EventType.ONGOING_MESSAGES);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<OngoingMessage> getOngoingMessages() {
        return ongoingMessages;
    }

    private void postEvent(EventType eventType) {
        EventBus.getDefault().post(new FirebaseEvent(eventType));
    }
}
