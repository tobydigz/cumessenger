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
    private ArrayList<OngoingMessage> ongoingMessages;

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
        databaseReference.child("messages").child(username).child(chatWithUsername).push().setValue(messageObject);
        databaseReference.child("messages").child(chatWithUsername).child(username).push().setValue(messageObject);
    }

    @Override
    public void queryForMessages() {
        databaseReference.child("messages").child(username).child(chatWithUsername).addValueEventListener(new ValueEventListener() {
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
            public void onCancelled(DatabaseError databaseError) {

            }
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
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

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
        databaseReference.addValueEventListener(new ValueEventListener() {
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
        databaseReference.child("forums").child(forumName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                forumMessages = null;
                forumMessages = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageObject messageObject = snapshot.getValue(MessageObject.class);
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
        databaseReference.child("forums/" + forumName).push().setValue(messageObject);
    }

    @Override
    public void queryForOngoingMessages(final String username) {
        databaseReference.child("messages").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    OngoingMessage ongoingMessage = new OngoingMessage();
                    ongoingMessage.setUserName(username);
                    ongoingMessage.setChatWithUsername(snapshot.getKey());
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
