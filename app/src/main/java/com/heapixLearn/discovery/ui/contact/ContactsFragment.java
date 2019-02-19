package com.heapixLearn.discovery.ui.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heapixLearn.discovery.R;
import java.util.ArrayList;

public class ContactsFragment extends Fragment implements SearchView.OnQueryTextListener {

    View contactsFragment;
    private ArrayList<Contact> contacts = new ArrayList<>();
    //TODO init manager
    private ContactManager contactManager = new com.heapixLearn.discovery.logic.contact.ContactManager();
    ContactListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        contactsFragment = inflater.inflate(R.layout.contact_fragment, container, false);

        Toolbar toolbar = contactsFragment.findViewById(R.id.contact_toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initContactRecyclerView();

        return contactsFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initContactRecyclerView(){
        if (contactManager != null) {
            contacts = contactManager.getAll();
            adapter = new ContactListAdapter(contacts, getContext());
            RecyclerView recyclerView = contactsFragment.findViewById(R.id.contact_recycler_view);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            Toast.makeText(getContext(), getString(R.string.unable_to_update_data),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        ArrayList<Contact> arrayList = new ArrayList<>();
        for(Contact contact : contacts){
            if(contact.getName().toLowerCase().contains(userInput)){
                arrayList.add(contact);
            }
        }
        adapter.updateList(arrayList);
        return true;
    }
}
