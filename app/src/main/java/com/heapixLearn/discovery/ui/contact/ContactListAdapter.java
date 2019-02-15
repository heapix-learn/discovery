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
    private ContactManager contactManager = new com.heapixLearn.discovery.logic.contact.ContactManager();
    Contact item;

    public ContactListAdapter(ArrayList<Contact> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, viewGroup,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        item = list.get(i);
        viewHolder.contactUsername.setText(item.getName());

        final Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                if(item.isFriend()){
                    item.setFriend(false);
                    viewHolder.contactButton.setBackgroundResource(R.drawable.follow);
                    Toast.makeText(context, context.getString(R.string.contact_unfollow), Toast.LENGTH_SHORT).show();
                } else {
                    item.setFriend(true);
                    viewHolder.contactButton.setBackgroundResource(R.drawable.unfollow);
                    Toast.makeText(context, context.getString(R.string.contact_follow), Toast.LENGTH_SHORT).show();
                }

            }
        };
        final Runnable onFailure = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, context.getString(R.string.contact_on_failure),
                        Toast.LENGTH_SHORT).show();
            }
        };

        if(item.getFollowers() == 0){
            viewHolder.contactFollowers.setText(R.string.no_followers);
        } else if(item.getFollowers() % 10 == 1){
            String followersOne = item.getFollowers() + " " + context.getString(R.string.followers_1);
            viewHolder.contactFollowers.setText(followersOne);
        } else if(item.getFollowers() % 10 >= 2 && item.getFollowers() % 10 <= 4) {
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
                if(contactManager != null){
                    item = list.get(i);
                    contactManager.update(item, onSuccess, onFailure);
                } else {
                    Toast.makeText(context, context.getString(R.string.unable_to_update_data),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateDataInList(Contact newContact){
        if (contactManager != null) {
            list.remove(newContact);
            list.add(newContact);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, context.getString(R.string.unable_to_update_data),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDataFromList(int id){
        if(contactManager != null){
            Contact deleteContact = contactManager.getById(id);
            list.remove(deleteContact);
            notifyDataSetChanged();
        } else {
            Toast.makeText(context, context.getString(R.string.unable_to_update_data),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void updateList(ArrayList<Contact> newList){
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
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
