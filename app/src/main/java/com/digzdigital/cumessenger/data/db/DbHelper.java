package com.digzdigital.cumessenger.data.db;


import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.ReminderItem;
import com.digzdigital.cumessenger.data.db.model.RowObject;
import com.digzdigital.cumessenger.data.db.model.User;

import java.util.ArrayList;


public interface DbHelper {

    void createCourse(Course course, String userId);
    void queryForCourses();
    ArrayList<Course> getAllCourses();
    boolean deleteCourse(String  key);
    boolean updateCourse(Course course);
    // void queryForReminders();
    // ArrayList<ReminderItem> getOnlineReminders();
    ArrayList<RowObject> getRowObjects();
    void updateUser(User user);
    void queryForUserInfo(String id);
    User getUserInfo();
    void queryForUsers(String userId);
    ArrayList<User> getUsers();
}
