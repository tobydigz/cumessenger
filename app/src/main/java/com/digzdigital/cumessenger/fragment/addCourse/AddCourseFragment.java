package com.digzdigital.cumessenger.fragment.addCourse;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.db.model.Course;
import com.digzdigital.cumessenger.databinding.FragmentAddCourseBinding;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddCourseFragment extends Fragment implements View.OnClickListener ,TimePickerDialog.OnTimeSetListener,AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentAddCourseBinding binding;
    private Date startTime, endTime;

private Course course = new Course();
    private OnFragmentInteractionListener listener;

    public AddCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddCourseFragment newInstance(String param1, String param2) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_course, container, false);
        binding.save.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
        binding.courseStartTime.setOnClickListener(this);
        binding.courseEndTime.setOnClickListener(this);
        initSpinner();
        return binding.getRoot();
    }

    private void initSpinner() {
        String[] ITEMS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ITEMS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.daySelect.setAdapter(adapter);
        binding.daySelect.setOnItemSelectedListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                listener.onSaveClicked(updateCourse());
                break;
            case R.id.cancel:
                listener.onCancelClicked();
                break;
            case R.id.courseStartTime:
                setTime("Set Start Time");
                break;
            case R.id.courseEndTime:
                setTime("Set End Time");
                break;
        }
    }

    private void setTime(String title) {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dialog = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);
        dialog.setTitle(title);
        dialog.show(getFragmentManager(), "Da");
    }

    private Course updateCourse() {

        course.setCourseTitle(binding.courseTitle.getText().toString());
        course.setVenue(binding.courseVenue.getText().toString());
        course.setStartTime(startTime);
        course.setEndTime(endTime);
        return course;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        switch (view.getId()) {
            case R.id.courseStartTime:
                startTime = createTime(hourOfDay, minute);
                binding.courseStartTime.setText(hourOfDay + ":" + minute);
                break;
            case R.id.courseEndTime:
                endTime = createTime(hourOfDay, minute);
                binding.courseEndTime.setText(hourOfDay + ":" + minute);
                break;
        }
    }

    private Date createTime(int hourOfDay, int minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // course.setDay(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onCancelClicked();
        void onSaveClicked(Course course);
    }


}
