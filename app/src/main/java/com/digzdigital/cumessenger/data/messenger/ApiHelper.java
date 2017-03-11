package com.digzdigital.cumessenger.data.messenger;


import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;

import java.util.ArrayList;
import java.util.Map;

public interface ApiHelper {

    void setChatUsers(String username, String chatWithUsername);
    void SendMessage(MessageObject messageObject);
    void queryForMessages();
    void queryForNewMessage();
    ArrayList<MessageObject> getMessages();
    MessageObject getNewMessage();
    void queryForForums();
    ArrayList<Forum> getFora();
    void queryForForumMessages(String forumName);
    ArrayList<MessageObject> getForumMessages();
    void postForumMessage(MessageObject messageObject, String forumName);
    void queryForOngoingMessages(String username);
    ArrayList<OngoingMessage> getOngoingMessages();

}
