package com.digzdigital.cumessenger.fragment.manageCourse;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.activity.MainActivity;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;
import com.digzdigital.cumessenger.fragment.editCourse.EditCourseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageCoursesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageCoursesFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public DataManager dataManager;
    // TODO: Rename and change types of parameters
    private String userId;
    private String mParam2;
    private ArrayList<Course> courses;
    private RecyclerView courseRV;
    private MainActivity activity;
    private Button addCourseButton;


    public ManageCoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageCoursesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(String userId, String param2) {
        ManageCoursesFragment fragment = new ManageCoursesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, userId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        activity = (MainActivity) getActivity();
        dataManager = activity.getDataManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_courses, container, false);
        courseRV = (RecyclerView) view.findViewById(R.id.courseRv);
        addCourseButton = (Button) view.findViewById(R.id.addCourse);
        addCourseButton.setOnClickListener(this);
        courses = dataManager.queryForCourses();
        doRest();
        return view;
    }


    protected void doRest() {
        courseRV.setLayoutManager(new StickyHeaderLayoutManager());
        if (courses != null) {
            if (courses.size() > 0) {
                ManageCourseListAdapter adapter = new ManageCourseListAdapter();
                adapter.setCourses(courses);
                courseRV.setAdapter(adapter);

                adapter.setOnItemClickListener(new ManageCourseListAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(Course course, View v) {
                        activity.switchFragment(EditCourseFragment.newInstance(course));
                    }
                });
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFirebaseEvent(FirebaseEvent event) {
        if (event.type == EventType.COURSES) {
            doRest();
        }
    }

    @Override
    public void onClick(View v) {
        activity.addNewCourse();
    }
}
