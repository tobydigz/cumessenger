package com.digzdigital.cumessenger.dagger;

import com.digzdigital.cumessenger.activity.MainActivity;
import com.digzdigital.cumessenger.data.AppDataManager;
import com.digzdigital.cumessenger.data.db.AppDbHelper;
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

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Digz on 11/03/2017.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(AppDataManager appDataManager);
    void inject(MainActivity mainActivity);
    // void inject(AddCourseFragment fragment);
    // void inject(EditFragment fragment);
    // void inject(ForumMessagesFragment fragment);
    // void inject(ForumFragment fragment);
    // void inject(ManageCoursesFragment fragment);
    // void inject(ChatFragment fragment);
    // void inject(OngoingMessagesListFragment fragment);
    // void inject(UsersFragment fragment);
    // void inject(ProfileFragment fragment);
    // void inject(TimetableFragment fragment);
}
