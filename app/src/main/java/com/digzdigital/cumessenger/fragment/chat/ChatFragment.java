package com.digzdigital.cumessenger.fragment.chat;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.messenger.ApiHelper;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.databinding.ActivityChatBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ChatFragment extends Fragment implements View.OnClickListener{

    private ActivityChatBinding binding;
    @Inject
    public ApiHelper apiHelper;

    private static final String ARG_PARAM_1 = "param1";
    private static final String ARG_PARAM_2 = "param2";
    private String username;
    private String chatWithUsername;

    public static ChatFragment newInstance(String username, String chatWithUsername){
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_1, username);
        args.putString(ARG_PARAM_2, chatWithUsername);
        fragment.setArguments(args);
        return fragment;
    }

    public ChatFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM_1);
            chatWithUsername = getArguments().getString(ARG_PARAM_2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_chat, container, false);
        binding.sendButton.setOnClickListener(this);

        return binding.getRoot();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.sendButton:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        apiHelper.setListenerForNewMessage();
        String messageText = binding.messageArea.getText().toString();
        if (!messageText.isEmpty()){
            Map<String, String> map = new HashMap<>();
            map.put("message", messageText);
            map.put("user", "username");// FIXME: 08/03/2017 dynamic username
            apiHelper.SendMessage(map);
        }
    }

    private void updateMessage(){
        MessageObject message = apiHelper.getNewMessage();

        addMessageBox(message);
    }

    private void addMessageBox(MessageObject message) {
        TextView textView = new TextView(getActivity());

        String messageText;
        if (message.isMine())messageText = "You:-\n" + message.getMessage();
        else messageText = message.getUserName() + ":-\n" + message.getMessage();

        textView.setText(messageText);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,0);
        textView.setLayoutParams(layoutParams);

        if (message.isMine())textView.setBackgroundResource(R.drawable.rounder_corner_1);
        else textView.setBackgroundResource(R.drawable.rounded_corner_2);

        binding.messagesLayout.addView(textView);
        binding.scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
