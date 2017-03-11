package com.digzdigital.cumessenger.fragment.forum.forumselect;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.messenger.model.Forum;

import java.util.ArrayList;

public class ForumListAdapter extends RecyclerView.Adapter<ForumListAdapter.ViewHolder> {

    private static MyClickListener myClickListener;
    private ArrayList<Forum> fora;

    public ForumListAdapter(ArrayList<Forum> fora) {
        this.fora = fora;
    }

    public Forum getItem(int position) {
        return fora.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Forum forum = getItem(position);

        holder.forumName.setText(forum.getTitle());

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return fora.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView forumName;
        ViewHolder(View itemView) {
            super(itemView);
            forumName = (TextView) itemView.findViewById(R.id.forumName);
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
