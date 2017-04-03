package com.digzdigital.cumessenger.fragment.forum.forumMessages;

import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.activity.MainActivity;
import com.digzdigital.cumessenger.data.DataManager;
import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;
import com.digzdigital.cumessenger.databinding.FragmentForumMessagesBinding;
import com.digzdigital.cumessenger.eventbus.EventType;
import com.digzdigital.cumessenger.eventbus.FirebaseEvent;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForumMessagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForumMessagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumMessagesFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Forum forum;
    public DataManager dataManager;
    private FragmentForumMessagesBinding binding;
    private OnFragmentInteractionListener listener;
    private ArrayList<MessageObject> forumMessages;
    private String userId, email;

    public ForumMessagesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param forum Parameter 1.
     * @return A new instance of fragment ForumMessagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForumMessagesFragment newInstance(Forum forum) {
        ForumMessagesFragment fragment = new ForumMessagesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity)getActivity();
        dataManager = activity.getDataManager();
        if (getArguments() != null) {
            forum = getArguments().getParcelable(ARG_PARAM1);
        }
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forum_messages, container, false);
        binding.sendButton.setOnClickListener(this);
        dataManager.queryForForumMessages(forum.getId());
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        sendMessage();
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
        if (event.type == EventType.FORUM_MESSAGES)loadForums();
    }

    private void loadForums() {
        forumMessages = dataManager.getForumMessages();
        doRest();
    }

    private void doRest() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.forumMessagesRv.setLayoutManager(linearLayoutManager);

        if(forumMessages!=null){
            if (forumMessages.size() >0){
                ForumMessagesListAdapter forumMessagesListAdapter = new ForumMessagesListAdapter(forumMessages);
                binding.forumMessagesRv.setAdapter(forumMessagesListAdapter);

                forumMessagesListAdapter.setOnItemClickListener(new ForumMessagesListAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                    }
                });
            }
        }
    }

    private void sendMessage() {
        String messageText = binding.messageArea.getText().toString();
        if (!messageText.isEmpty()) {
            MessageObject messageObject = new MessageObject();
            messageObject.setMessage(messageText);
            messageObject.setUserName(email);
            messageObject.setUid(userId);
            dataManager.postForumMessage(messageObject, forum.getId());
        }
    }



}
