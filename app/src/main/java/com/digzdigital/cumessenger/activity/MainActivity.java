package com.digzdigital.cumessenger.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
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
import android.widget.Toast;

import com.digzdigital.cumessenger.MessengerApplication;
import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;
import com.digzdigital.cumessenger.fragment.addCourse.AddCourseFragment;
import com.digzdigital.cumessenger.fragment.editProfile.EditFragment;
import com.digzdigital.cumessenger.fragment.forum.forumMessages.ForumMessagesFragment;
import com.digzdigital.cumessenger.fragment.forum.forumselect.ForumFragment;
import com.digzdigital.cumessenger.fragment.manageCourse.ManageCoursesFragment;
import com.digzdigital.cumessenger.fragment.messaging.chat.ChatFragment;
import com.digzdigital.cumessenger.fragment.messaging.ongoing.OngoingMessagesListFragment;
import com.digzdigital.cumessenger.fragment.messaging.user.UsersFragment;
import com.digzdigital.cumessenger.fragment.profile.ProfileFragment;
import com.digzdigital.cumessenger.fragment.timetable.TimetableFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProfileFragment.ProfileFragmentListener, EditFragment.EditFragmentListener, OngoingMessagesListFragment.OnFragmentInteractionListener {

    @Inject
    public DataManager dataManager;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private Fragment profileFragment, editFragment, addCourseFragment, manageCourseFragment, usersFragment, timetableFragment;
    private FragmentManager fragmentManager;
    private AuthStateListener listener = new AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
                //Nigga signed in
                loadDetails();
            } else {
                //Nigga signed out
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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
        ((MessengerApplication) getApplication()).getAppComponent().inject(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getFragmentManager();
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(listener);

    }

    private void loadDetails() {
        if (getIntent().getStringExtra("id") != null) {
            String id = getIntent().getStringExtra("id");
            User user = new User();
            user.setId(id);
            user.setUid(firebaseUser.getUid());
            user.setEmail(firebaseUser.getEmail());
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
            switchFragment(createCoursesFragment());
        } else if (id == R.id.nav_forum) {
            switchFragment(createForumFragment());
        } else if (id == R.id.nav_messaging) {
            switchFragment(createOngoingMessagesFragment());
        } else if (id == R.id.nav_signout) {
            auth.signOut();
        } else if (id == R.id.nav_people) {
            switchFragment(createUsersFragment());
        }else if (id == R.id.nav_timetable) {
            switchFragment(createTimeTableFragment());
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

    public void switchFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .addToBackStack(null)
                .commit();
    }

    private Fragment createEditFragment(User user) {
        return EditFragment.newInstance(user);
    }

    private Fragment createChatFragment( String chatWithUserid, String uid) {
        return ChatFragment.newInstance(firebaseUser.getEmail(), chatWithUserid, uid);
    }
    private Fragment createTimeTableFragment() {
        return TimetableFragment.newInstance("", "");
    }

    private Fragment createUsersFragment() {
        return UsersFragment.newInstance(firebaseUser.getUid(), "");
    }

    private Fragment createCoursesFragment() {
        return ManageCoursesFragment.newInstance(firebaseUser.getUid(), "");
    }

    private Fragment createOngoingMessagesFragment() {
        return OngoingMessagesListFragment.newInstance(firebaseUser.getUid());
    }

    private Fragment createForumFragment() {
        return ForumFragment.newInstance("", "");
    }

    private Fragment getProfileFragment() {
        if (profileFragment == null)
            profileFragment = ProfileFragment.newInstance(firebaseUser.getUid());
        return profileFragment;
    }

    @Override
    public void onSaveChanges(User user) {
        updateFirebaseUser(user);
        switchFragment(getProfileFragment());
    }

    private void updateFirebaseUser(User user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getName())
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    public void onSaveClicked(Course course) {
        if (dataManager.createCourse(course, firebaseUser.getUid()))
            switchFragmentNoStack(getCourseFragment());
        else showError();

    }

    public void onCancelClicked() {
        switchFragmentNoStack(getCourseFragment());

    }
    private void showError() {
        Toast.makeText(this, "Course not saved", Toast.LENGTH_LONG).show();
    }

    public void onUpdateClicked(Course course) {
        dataManager.updateCourse(course, "");
        switchFragmentNoStack(getCourseFragment());

    }

    public void addNewCourse() {
        switchFragmentNoStack(getAddCourseFragment());
    }

    public void deleteCourse(Course course) {
        dataManager.deleteCourse(course);
        switchFragmentNoStack(getCourseFragment());
    }

    private Fragment getCourseFragment() {
        return ManageCoursesFragment.newInstance(firebaseUser.getUid(), "");
    }

    private Fragment getAddCourseFragment() {
        return AddCourseFragment.newInstance(firebaseUser.getUid(), "");
    }

    public void onCourseClicked(Course course) {

    }

    public void onUserClicked(String username) {
        switchFragment(createChatFragment(username, firebaseUser.getUid()));
    }

    @Override
    public void onOngoingMessageClicked(OngoingMessage ongoingMessage) {
        createChatFragment(ongoingMessage.getChatWithUserId(), firebaseUser.getUid());
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void onForumClicked(Forum forum) {
        switchFragment(getForumFragment(forum));
    }

    private Fragment getForumFragment(Forum forum) {
        return ForumMessagesFragment.newInstance(forum);
    }

    private void switchFragmentNoStack(Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit();
    }
}
