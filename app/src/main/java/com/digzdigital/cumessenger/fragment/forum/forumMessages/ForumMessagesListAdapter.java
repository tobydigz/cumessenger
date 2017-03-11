package com.digzdigital.cumessenger.fragment.forum.forumMessages;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.messenger.model.Forum;
import com.digzdigital.cumessenger.data.messenger.model.MessageObject;

import java.util.ArrayList;

class ForumMessagesListAdapter extends RecyclerView.Adapter<ForumMessagesListAdapter.ViewHolder> {

    private static MyClickListener myClickListener;
    private ArrayList<MessageObject> forumMessages;

    public ForumMessagesListAdapter(ArrayList<MessageObject> forumMessages) {
        this.forumMessages = forumMessages;
    }

    public MessageObject getItem(int position) {
        return forumMessages.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MessageObject messageObject = getItem(position);

        holder.forumUser.setText(messageObject.getUserName());
        holder.forumMessage.setText(messageObject.getMessage());

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return forumMessages.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView forumUser;
        TextView forumMessage;

        ViewHolder(View itemView) {
            super(itemView);
            forumUser = (TextView) itemView.findViewById(R.id.forumUser);
            forumMessage = (TextView) itemView.findViewById(R.id.forumMessage);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);

        }


    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
}
