package com.digzdigital.cumessenger.data.messenger;


import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;

import java.util.ArrayList;
import java.util.Map;

public interface ApiHelper {

    void SendMessage(Map<String, String> map);
    void queryForNewMessage();
    MessageObject getNewMessage();
    void queryForForums();
    ArrayList<Forum> getForumGroups();
    void queryForForumGroups();
    void queryForForumMessages();
    ArrayList<MessageObject> getForumMessages();

}
