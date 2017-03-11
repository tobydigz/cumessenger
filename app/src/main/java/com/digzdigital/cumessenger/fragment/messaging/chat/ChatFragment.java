package com.digzdigital.cumessenger.fragment.messaging.chat;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.databinding.ActivityChatBinding;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

public class ChatFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM_1 = "param1";
    private static final String ARG_PARAM_2 = "param2";
    private static final String ARG_PARAM_3 = "param3";
    @Inject
    public DataManager dataManager;
    private ActivityChatBinding binding;
    private ArrayList<MessageObject> messages;
    private String username;
    private String chatWithUsername;
    private String uid;

    public ChatFragment() {

    }

    public static Fragment newInstance(String username, String chatWithUsername, String uid) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_1, username);
        args.putString(ARG_PARAM_2, chatWithUsername);
        args.putString(ARG_PARAM_3, uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM_1);
            chatWithUsername = getArguments().getString(ARG_PARAM_2);
            uid = getArguments().getString(ARG_PARAM_3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_chat, container, false);
        binding.sendButton.setOnClickListener(this);
        dataManager.queryForMessages();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sendButton:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        dataManager.setChatUsers(username, chatWithUsername);
        String messageText = binding.messageArea.getText().toString();
        if (!messageText.isEmpty()) {
            MessageObject messageObject = new MessageObject();
            messageObject.setMessage(messageText);
            messageObject.setUserName(username);
            messageObject.setUid(uid);
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
        TextView textView = new TextView(getActivity());

        String messageText;
        if (message.getUId().equals(uid)) messageText = "You:-\n" + message.getMessage();
        else messageText = message.getUserName() + ":-\n" + message.getMessage();

        textView.setText(messageText);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        textView.setLayoutParams(layoutParams);

        if (message.getUId().equals(uid))
            textView.setBackgroundResource(R.drawable.rounder_corner_1);
        else textView.setBackgroundResource(R.drawable.rounded_corner_2);

        binding.messagesLayout.addView(textView);
        binding.scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
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
}
