package com.digzdigital.cumessenger.data.messenger;

import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Digz on 09/03/2017.
 */

public class AppApiHelper implements ApiHelper {
    @Override
    public void SendMessage(Map<String, String> map) {

    }

    @Override
    public void queryForMessage() {

    }

    @Override
    public MessageObject getNewMessage() {
        return null;
    }

    @Override
    public void queryForForums() {

    }

    @Override
    public ArrayList<Forum> getForumGroups() {
        return null;
    }

    @Override
    public void queryForForumGroups() {

    }

    @Override
    public void queryForForumMessages() {

    }

    @Override
    public ArrayList<MessageObject> getForumMessages() {
        return null;
    }
}
