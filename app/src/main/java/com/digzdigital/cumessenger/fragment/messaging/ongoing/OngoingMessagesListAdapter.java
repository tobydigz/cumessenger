package com.digzdigital.cumessenger.fragment.messaging.ongoing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.messenger.model.OngoingMessage;

import java.util.ArrayList;

public class OngoingMessagesListAdapter extends RecyclerView.Adapter<OngoingMessagesListAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    private ArrayList<OngoingMessage> ongoingMessages;

    public OngoingMessagesListAdapter(ArrayList<OngoingMessage> ongoingMessages) {
        this.ongoingMessages = ongoingMessages;
    }

    public OngoingMessage getItem(int position) {
        return ongoingMessages.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ongoing_message, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        OngoingMessage ongoingMessage = getItem(position);

        holder.textField.setText("Chat with" + ongoingMessage.getChatWithUsername());
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return ongoingMessages.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView textField;

        ViewHolder(View itemView) {
            super(itemView);
            textField = (TextView) itemView.findViewById(R.id.textField);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
