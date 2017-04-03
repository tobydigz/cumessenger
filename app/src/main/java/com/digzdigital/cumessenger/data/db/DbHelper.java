package com.digzdigital.cumessenger.data.db;


import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.ReminderItem;
import com.digzdigital.cumessenger.data.db.model.RowObject;
import com.digzdigital.cumessenger.data.db.model.User;

import java.util.ArrayList;


public interface DbHelper {

    String getUsername();
    boolean createCourse(Course course, String userId);
    ArrayList<Course> queryForCourses();
    ArrayList<Course> getAllCourses();
    boolean deleteCourse(Course course);
    boolean updateCourse(Course course, String userId);
    // void queryForReminders();
    // ArrayList<ReminderItem> getOnlineReminders();
    ArrayList<RowObject> getRowObjects();
    void updateUser(User user);
    void queryForUserInfo(String id);
    User getUserInfo();
    void queryForUsers(String userId);
    void searchForUsers(String userId);
    ArrayList<User> getUsers();
}
