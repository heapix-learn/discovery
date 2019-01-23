package com.heapixLearn.discovery.ui.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapixLearn.discovery.R;
import java.util.ArrayList;

public class ContactsFragment extends Fragment {

    View contactsFragment;
    private ArrayList<Contact> contacts = new ArrayList<>();
    //TODO init manager
    private ContactManager contactManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactsFragment = inflater.inflate(R.layout.contact_fragment, container, false);

        initContactRecyclerView();

        return contactsFragment;
    }

    private void initContactRecyclerView(){
        //TODO
        contacts = contactManager.getAll();
        ContactListAdapter adapter = new ContactListAdapter(contacts, getContext());
        RecyclerView recyclerView = contactsFragment.findViewById(R.id.contact_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
