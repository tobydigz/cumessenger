package com.digzdigital.cumessenger.fragment.messaging.chat;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.activity.MainActivity;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.databinding.ActivityChatBinding;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatFragment extends Fragment implements ChatView.OnSentMessageListener {

    private static final String ARG_PARAM_1 = "param1";
    private static final String ARG_PARAM_2 = "param2";
    private static final String ARG_PARAM_3 = "param3";
    private static final String ARG_PARAM_4 = "param4";
    public DataManager dataManager;
    private ActivityChatBinding binding;
    private ArrayList<MessageObject> messages;
    private String username;
    private String chatWithUserid;
    private String uid;
    private String email;

    public ChatFragment() {

    }

    public static Fragment newInstance(String username, String chatWithUserid, String uid, String email) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_1, username);
        args.putString(ARG_PARAM_2, chatWithUserid);
        args.putString(ARG_PARAM_3, uid);
        args.putString(ARG_PARAM_4, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        dataManager = activity.getDataManager();
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM_1);
            chatWithUserid = getArguments().getString(ARG_PARAM_2);
            uid = getArguments().getString(ARG_PARAM_3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_chat, container, false);
        dataManager.queryForMessages(uid, chatWithUserid);
        binding.chatView.setOnSentMessageListener(this);
        return binding.getRoot();
    }

    private void prepSendMessage(ChatMessage message) {
        dataManager.setChatUsers(uid, chatWithUserid);
        String messageText = message.getMessage();
        if (!messageText.isEmpty()) {
            MessageObject messageObject = new MessageObject();
            messageObject.setMessage(messageText);
            messageObject.setUserName(username);
            messageObject.setUid(uid);
            messageObject.setChatWithUserName(email);
            messageObject.setDate(message.getTimestamp());
            dataManager.SendMessage(messageObject);
        }
    }

    private void updateMessage() {
        MessageObject message = dataManager.getNewMessage();
        addMessageBox(message);
    }

    private void loadMessages() {
        messages = dataManager.getMessages();
        for (MessageObject messageObject : messages) {
            addMessageBox(messageObject);
        }
    }

    private void addMessageBox(MessageObject message) {

        String messageText = message.getMessage();
        ChatMessage.Type type;
        if (message.getUId().equals(uid)) type = ChatMessage.Type.SENT;
        else type = ChatMessage.Type.RECEIVED;

        long date = message.getDate();
        ChatMessage chatMessage = new ChatMessage(messageText, date, type);
        binding.chatView.addMessage(chatMessage);


    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFirebaseEvent(FirebaseEvent event) {
        if (event.type == EventType.NEW_MESSAGE) updateMessage();
        if (event.type == EventType.MESSAGES) {
            loadMessages();
            dataManager.queryForNewMessage();
        }
    }

    @Override
    public boolean sendMessage(ChatMessage chatMessage) {
        prepSendMessage(chatMessage);
        return true;
    }
}
