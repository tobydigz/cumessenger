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
    public String getUsername(){

        return userInfo.getId();
    }

    @Override
    public boolean createCourse(Course course, String userId) {
        if ((Course.find(Course.class, "time = ? and day = ?", course.getTime().toString(), course.getDay().toString())).size() > 0)
            return false;
        course.save();
        return true;

    }

    @Override
    public ArrayList<Course> queryForCourses() {
        return new ArrayList<>(Course.findWithQuery(Course.class, "Select * from Course ORDER BY day"));
        // return new ArrayList<>(Course.listAll(Course.class));
    }


    @Override
    public ArrayList<Course> getAllCourses() {
        return courses;
    }

    @Override
    public boolean deleteCourse(Course course) {
        Course course1 = Course.findById(Course.class, course.getId());
        course1.delete();
        return false;
    }

    @Override
    public boolean updateCourse(Course course, String userId) {
        Course course1 = Course.findById(Course.class, course.getId());
        course1.setCourseTitle(course.getCourseTitle());
        course1.setVenue(course.getVenue());
        course1.setCourseCode(course.getCourseCode());
        course1.setDuration(course.getDuration());
        course1.setDay(course.getDay());
        course1.setTime(course.getTime());
        course1.save();
        return false;
    }


    private ArrayList<RowObject> createRowObjects() {
        ArrayList<Course> results = queryForCourses();

        RowObjectsCreator creator = new RowObjectsCreator(results);

        return creator.getRows();
    }


    @Override
    public ArrayList<RowObject> getRowObjects() {
        return createRowObjects();
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
        databaseReference.child("usersKnown").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = null;
                users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = new User();
                    String firstName = (String)snapshot.child("firstName").getValue();
                    String lastName = (String)snapshot.child("lastName").getValue();
                    String id = snapshot.getKey();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setUid(id);
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
    public void searchForUsers(String param) {
        databaseReference.child("usersKnown").child(param).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = null;
                users = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = new User();
                    String firstName = (String)snapshot.child("firstName").getValue();
                    String lastName = (String)snapshot.child("lastName").getValue();
                    String email = (String)snapshot.child("email").getValue();
                    String id = (String)snapshot.child("id").getValue();
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setId(id);
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
