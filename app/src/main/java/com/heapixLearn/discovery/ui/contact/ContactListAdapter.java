package com.heapixLearn.discovery.ui.contact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heapixLearn.discovery.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<Contact> list = new ArrayList<>();
    private Context context;
    //TODO init manager
    private ContactManager contactManager;
    Contact item;

    private Runnable onSuccess = new Runnable() {
        @Override
        public void run() {
            if(item.isFriend()){
                Toast.makeText(context, context.getString(R.string.contact_follow), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, context.getString(R.string.contact_unfollow), Toast.LENGTH_SHORT).show();
            }

        }
    };
    private Runnable onFailure = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(context, context.getString(R.string.contact_on_failure), Toast.LENGTH_SHORT).show();
        }
    };

    public ContactListAdapter(ArrayList<Contact> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        item = list.get(i);
        viewHolder.contactUsername.setText(item.getName());

        if(item.getFollowers() == 0){
            viewHolder.contactFollowers.setText(R.string.no_followers);
        } else if(item.getFollowers() == 1){
            String followersOne = item.getFollowers() + " " + context.getString(R.string.followers_1);
            viewHolder.contactFollowers.setText(followersOne);
        } else if(item.getFollowers() >= 2 && item.getFollowers() <= 4) {
            String followersTwoFour = item.getFollowers() + " " + context.getString(R.string.followers_2_4);
            viewHolder.contactFollowers.setText(followersTwoFour);
        } else {
            String followersDefault = item.getFollowers() + " " + context.getString(R.string.followers_default);
            viewHolder.contactFollowers.setText(followersDefault);
        }

        Glide.with(context)
                .asBitmap()
                .load(item.getAvatar())
                .into(viewHolder.contactAvatar);
        if(item.isFriend()){
            viewHolder.contactButton.setBackgroundResource(R.drawable.unfollow);
        } else {
            viewHolder.contactButton.setBackgroundResource(R.drawable.follow);
        }
        viewHolder.contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = list.get(i);
                if(item.isFriend()){
                    item.setFriend(false);
                    viewHolder.contactButton.setBackgroundResource(R.drawable.follow);
                    contactManager.update(item, onSuccess, onFailure);
                } else {
                    item.setFriend(true);
                    viewHolder.contactButton.setBackgroundResource(R.drawable.unfollow);
                    contactManager.update(item, onSuccess, onFailure);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView contactAvatar;
        TextView contactUsername;
        TextView contactFollowers;
        Button contactButton;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactAvatar = itemView.findViewById(R.id.contact_avatar);
            contactUsername = itemView.findViewById(R.id.contact_name);
            contactFollowers = itemView.findViewById(R.id.contact_followers);
            contactButton = itemView.findViewById(R.id.contact_btn_follow);
        }
    }
}
