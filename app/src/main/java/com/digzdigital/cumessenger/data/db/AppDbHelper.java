package com.digzdigital.cumessenger.data.db;

import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.RowObject;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Digz on 09/03/2017.
 */

public class AppDbHelper implements DbHelper {

    private ArrayList<Course> courses;
    private User userInfo;
    private ArrayList<User> users;
    private DatabaseReference databaseReference;

    public AppDbHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void createCourse(Course course, String userId) {

        databaseReference.child("userCourses").child(userId).child(course.getId()).setValue(course);
    }

    @Override
    public void queryForCourses() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                courses = null;
                courses = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Course course = snapshot.getValue(Course.class);
                    courses.add(course);
                }
                postEvent(EventType.COURSES);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<Course> getAllCourses() {
        return courses;
    }

    @Override
    public boolean deleteCourse(String key) {
        databaseReference.child(key).removeValue();
        return false;
    }

    @Override
    public boolean updateCourse(Course course) {
        databaseReference.setValue(course);
        return false;
    }


    @Override
    public ArrayList<RowObject> getRowObjects() {
        return null;
    }

    @Override
    public void updateUser(User user) {
        databaseReference.child("users").child(user.getUid()).setValue(user);
    }

    @Override
    public void queryForUserInfo(String id) {
        databaseReference.child("users").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInfo = dataSnapshot.getValue(User.class);
                postEvent(EventType.USER);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public User getUserInfo() {
        return userInfo;
    }

    @Override
    public void queryForUsers(String userId) {
        databaseReference.child("users").child(userId).child("usersKnown").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = null;
                users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
                postEvent(EventType.USERS);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    private void postEvent(EventType eventType) {
        EventBus.getDefault().post(new FirebaseEvent(eventType));
    }
}
