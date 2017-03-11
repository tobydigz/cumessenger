package com.digzdigital.cumessenger.fragment.messaging.ongoing;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;
import com.digzdigital.cumessenger.databinding.FragmentMessagesListBinding;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OngoingMessagesListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OngoingMessagesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OngoingMessagesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String username;
    private String mParam2;
    private FragmentMessagesListBinding binding;
    private DataManager dataManager;
    private ArrayList<OngoingMessage> ongoingMessages;

    private OnFragmentInteractionListener listener;

    public OngoingMessagesListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @return A new instance of fragment OngoingMessagesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OngoingMessagesListFragment newInstance(String username) {
        OngoingMessagesListFragment fragment = new OngoingMessagesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_messages_list, container, false);
        dataManager.queryForOngoingMessages(username);
        return binding.getRoot();
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
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFirebaseEvent(FirebaseEvent event){
        if (event.type == EventType.ONGOING_MESSAGES)loadOngoingMessages();
    }

    private void loadOngoingMessages() {
        ongoingMessages = dataManager.getOngoingMessages();
        doRest();
    }

    private void doRest() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.ongoingRv.setLayoutManager(linearLayoutManager);

        if(ongoingMessages!=null){
            if (ongoingMessages.size() >0){
                OngoingMessagesListAdapter ongoingMessagesListAdapter = new OngoingMessagesListAdapter(ongoingMessages);
                binding.ongoingRv.setAdapter(ongoingMessagesListAdapter);

                ongoingMessagesListAdapter.setOnItemClickListener(new OngoingMessagesListAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        OngoingMessage ongoingMessage = ongoingMessages.get(position);
                        listener.onOngoingMessageClicked(ongoingMessage);
                    }
                });
            }
        }
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
        // TODO: Update argument type and name
        void onOngoingMessageClicked(OngoingMessage ongoingMessage);
    }
}
