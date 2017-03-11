package com.digzdigital.cumessenger.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.db.DbHelper;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;
import com.digzdigital.cumessenger.fragment.addCourse.AddCourseFragment;
import com.digzdigital.cumessenger.fragment.editProfile.EditFragment;
import com.digzdigital.cumessenger.fragment.manageCourse.ManageCoursesFragment;
import com.digzdigital.cumessenger.fragment.messaging.chat.ChatFragment;
import com.digzdigital.cumessenger.fragment.messaging.ongoing.OngoingMessagesListFragment;
import com.digzdigital.cumessenger.fragment.messaging.user.UsersFragment;
import com.digzdigital.cumessenger.fragment.profile.ProfileFragment;
import com.digzdigital.cumessenger.fragment.timetable.TimetableFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.ProfileFragmentListener, EditFragment.EditFragmentListener, AddCourseFragment.OnFragmentInteractionListener, ManageCoursesFragment.OnFragmentInteractionListener, UsersFragment.OnFragmentInteractionListener, TimetableFragment.OnFragmentInteractionListener, OngoingMessagesListFragment.OnFragmentInteractionListener {

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private Fragment profileFragment, editFragment, addCourseFragment, manageCourseFragment, usersFragment, timetableFragment;
    private FragmentManager fragmentManager;
    private DbHelper dbHelper;
    private AuthStateListener listener = new AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                //Nigga signed in
                loadDetails();
            } else {
                //Nigga signed out
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getFragmentManager();
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(listener);
        //// TODO: 25/02/2017 start Fragment based on signed in user

    }

    private void loadDetails() {
        if (getIntent() != null) {
            String id = getIntent().getStringExtra("id");
            User user = new User();
            user.setId(id);
            user.setUid(firebaseUser.getEmail());
            switchFragment(createEditFragment(user));
        } else {
            switchFragment(getProfileFragment());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            switchFragment(getProfileFragment());
        } else if (id == R.id.nav_courses) {

        } else if (id == R.id.nav_forum) {

        } else if (id == R.id.nav_messaging) {

        } else if (id == R.id.nav_signout) {

        }else if (id == R.id.nav_people) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    @Override
    public void onEditClicked(User user) {
        switchFragment(createEditFragment(user));
    }

    private void switchFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
    }

    private Fragment createEditFragment(User user) {
        return EditFragment.newInstance(user);
    }

    private Fragment createChatFragment(String username, String chatWithUsername, String uid) {
        return ChatFragment.newInstance(username, chatWithUsername, uid);
    }

    private Fragment createUsersFragment() {
        return UsersFragment.newInstance("", "");
    }

    private Fragment getProfileFragment() {
        if (profileFragment == null)
            profileFragment = ProfileFragment.newInstance(firebaseUser.getUid());
        return profileFragment;
    }

    @Override
    public void onSaveChanges(String name) {
        //todo updateFirebaseUser
        switchFragment(getProfileFragment());
    }

    @Override
    public void onCancelClicked() {

    }

    @Override
    public void onSaveClicked(Course course) {
        dbHelper.createCourse(course, firebaseUser.getUid());
    }

    @Override
    public void onCourseClicked(Course course) {

    }

    @Override
    public void onUserClicked(String username) {
        switchFragment(createChatFragment(firebaseUser.getUid(), username, firebaseUser.getUid()));
    }

    @Override
    public void onOngoingMessageClicked(OngoingMessage ongoingMessage) {
        createChatFragment(ongoingMessage.getUserName(), ongoingMessage.getChatWithUsername(), firebaseUser.getUid());
    }
}
