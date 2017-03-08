package com.digzdigital.cumessenger.fragment.editProfile;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.db.DbHelper;
import com.digzdigital.cumessenger.data.db.model.User;
import com.digzdigital.cumessenger.databinding.FragmentEditBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditFragment.EditFragmentListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference databaseReference;
    private FragmentEditBinding binding;
    // TODO: Rename and change types of parameters
    private User user = null;

    private DbHelper dbHelper;

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
    public static EditFragment newInstance(User user) {
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
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        if (user != null) updateUI();
        return binding.getRoot();
    }

    private void updateUI() {
        binding.profileName.setText(user.getName());
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
        user.setFirstName("");
        user.setLastName("");
        user.setMiddleName("");
        user.setDepartment("");
        user.setProgramme("");
        user.setDateOfBirth("");
        user.setStatus("");
        user.setGraduationYear(2018);
        user.setPhoneNumber("");
        user.setEmail("");
        user.setId("");
        dbHelper.updateUser(user);
        // databaseReference.child(user.getId()).setValue(user);
        listener.onSaveChanges();
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
        void onSaveChanges();
    }
}
