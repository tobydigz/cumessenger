package com.digzdigital.cumessenger.data;

import com.digzdigital.cumessenger.data.db.DbHelper;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.RowObject;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.data.messenger.ApiHelper;
import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;

import java.util.ArrayList;

import javax.inject.Inject;

public class AppDataManager implements DataManager {

    @Inject
    public DbHelper dbHelper;
    @Inject
    public ApiHelper apiHelper;

    public AppDataManager() {

    }

    @Override
    public void createCourse(Course course, String userId) {
        dbHelper.createCourse(course, userId);
    }

    @Override
    public void queryForCourses() {
        dbHelper.queryForCourses();
    }

    @Override
    public ArrayList<Course> getAllCourses() {
        return dbHelper.getAllCourses();
    }

    @Override
    public boolean deleteCourse(String key) {
        dbHelper.deleteCourse(key);
        return true;
    }

    @Override
    public boolean updateCourse(Course course) {
        dbHelper.updateCourse(course);
        return false;
    }

    @Override
    public ArrayList<RowObject> getRowObjects() {
        return dbHelper.getRowObjects();
    }

    @Override
    public void updateUser(User user) {
        dbHelper.updateUser(user);
    }

    @Override
    public void queryForUserInfo(String id) {
        dbHelper.queryForUserInfo(id);
    }

    @Override
    public User getUserInfo() {
        return dbHelper.getUserInfo();
    }

    @Override
    public void queryForUsers(String userId) {
        dbHelper.queryForUsers(userId);
    }

    @Override
    public ArrayList<User> getUsers() {
        return dbHelper.getUsers();
    }

    @Override
    public void setChatUsers(String username, String chatWithUsername) {
        apiHelper.setChatUsers(username, chatWithUsername);
    }

    @Override
    public void SendMessage(MessageObject messageObject) {
        apiHelper.SendMessage(messageObject);
    }

    @Override
    public void queryForMessages() {
        apiHelper.queryForMessages();
    }

    @Override
    public void queryForNewMessage() {
        apiHelper.queryForNewMessage();
    }

    @Override
    public ArrayList<MessageObject> getMessages() {
        return apiHelper.getMessages();
    }

    @Override
    public MessageObject getNewMessage() {
        return apiHelper.getNewMessage();
    }

    @Override
    public void queryForForums() {
        apiHelper.queryForForums();
    }

    @Override
    public ArrayList<Forum> getFora() {
        return apiHelper.getFora();
    }

    @Override
    public void queryForForumMessages(String forumName) {
        apiHelper.queryForForumMessages(forumName);
    }

    @Override
    public ArrayList<MessageObject> getForumMessages() {
        return apiHelper.getForumMessages();
    }

    @Override
    public void postForumMessage(MessageObject messageObject, String forumName) {
        apiHelper.postForumMessage(messageObject, forumName);
    }

    @Override
    public void queryForOngoingMessages(String username) {
        apiHelper.queryForOngoingMessages(username);
    }

    @Override
    public ArrayList<OngoingMessage> getOngoingMessages() {
        return apiHelper.getOngoingMessages();
    }
}
