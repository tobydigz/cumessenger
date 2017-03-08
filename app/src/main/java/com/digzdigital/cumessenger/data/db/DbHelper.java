package com.digzdigital.cumessenger.data.db;


import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.ReminderItem;
import com.digzdigital.cumessenger.data.db.model.RowObject;
import com.digzdigital.cumessenger.data.db.model.User;

import java.util.ArrayList;


public interface DbHelper {

    void createCourse(Course course);
    void queryForCourses();
    ArrayList<Course> getAllCourses();
    boolean deleteCourse(long key);
    boolean updateCourse(Course course);
    void queryForReminders();
    ArrayList<ReminderItem> getOnlineReminders();
    ArrayList<RowObject> getRowObjects();
    void updateUser(User user);
    void queryForUserInfo();
    User getUserInfo(String id);
    void queryForUsers();
    ArrayList<User> getUsers();
}
