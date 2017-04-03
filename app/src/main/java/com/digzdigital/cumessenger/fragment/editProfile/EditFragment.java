package com.digzdigital.cumessenger.fragment.editProfile;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.activity.MainActivity;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.databinding.FragmentEditBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.EditFragmentListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference databaseReference;
    private FragmentEditBinding binding;
    private User user = null;

    private DataManager dataManager;

    private EditFragmentListener listener;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment newInstance(User user) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false);
        binding.saveUser.setOnClickListener(this);
        MainActivity activity = (MainActivity)getActivity();
        dataManager = activity.getDataManager();
        if (user != null) {
            try {
                updateUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return binding.getRoot();
    }

    private void updateUI() throws Exception{

        binding.profileFirstName.setText(user.getFirstName());
        binding.profileMiddleName.setText(user.getMiddleName());
        binding.profileLastName.setText(user.getLastName());
        binding.profileYear.setText(user.getGraduationYear());
        binding.userDepartment.setText(user.getDepartment());
        binding.userProgramme.setText(user.getProgramme());
        binding.userDOB.setText(user.getDateOfBirth());
        binding.userStatus.setText(user.getStatus());
        binding.userEmail.setText(user.getEmail());
        binding.userPhone.setText(user.getPhoneNumber());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditFragmentListener) {
            listener = (EditFragmentListener) context;
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
        writeToDb();
    }

    private void writeToDb() {
        if (user == null) user = new User();
        user.setFirstName(binding.profileFirstName.getText().toString());
        user.setLastName(binding.profileMiddleName.getText().toString());
        user.setMiddleName(binding.profileLastName.getText().toString());
        user.setDepartment(binding.userDepartment.getText().toString());
        user.setProgramme(binding.userProgramme.getText().toString());
        user.setDateOfBirth(binding.userDOB.getText().toString());
        user.setStatus(binding.userStatus.getText().toString());
        user.setGraduationYear(Integer.parseInt(binding.profileYear.getText().toString()));
        user.setPhoneNumber(binding.userPhone.getText().toString());
        dataManager.updateUser(user);
        MainActivity activity = (MainActivity)getActivity();
        activity.onSaveChanges(user);
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
    public interface EditFragmentListener {
        // TODO: Update argument type and name
        void onSaveChanges(User user);
    }
}
