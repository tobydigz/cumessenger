package com.digzdigital.cumessenger.fragment.messaging.user;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.digzdigital.cumessenger.R;
import com.digzdigital.cumessenger.data.db.model.User;

import java.util.ArrayList;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {
    private static MyClickListener myClickListener;
    private ArrayList<User> users;
    private Context context;

    public UsersListAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        User user = getItem(position);

        holder.userName.setText(user.getName());
        // holder.userId.setText(user.getId());
        // TODO: 08/03/2017 use glide/picasso to load image
        // holder.forumImage.setImageResource(forum.getImageResId());

        /*byte[] image = null;
        Bitmap bitmap = null;
        image = person.getImage();
        if (image != null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, options); //Convert bytearray to bitmap
            //for performance free the memmory allocated by the bytearray and the blob variable
            image = null;
            if (bitmap != null) {
                holder.contactImage.setImageBitmap(bitmap);
                System.out.println("bitmap is not null");
            } else {
                System.out.println("bitmap is null");
            }
        }
*/



//        holder.viewDate.setText(devotional.getDate());


    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView userName, userId;
        ImageView userImage;
        ViewHolder(View itemView) {
            super(itemView);
            // userId = (TextView) itemView.findViewById(R.id.userId);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
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
