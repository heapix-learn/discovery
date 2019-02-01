package com.example.discovery;

import android.support.test.runner.AndroidJUnit4;

import com.heapixLearn.discovery.AppContext;
import com.heapixLearn.discovery.ui.contact.Contact;
import com.heapixLearn.discovery.ui.contact.ContactListAdapter;
import com.heapixLearn.discovery.ui.contact.ContactManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ContactTest {
    ArrayList<Contact> contacts;
    ContactListAdapter adapter;
    ContactManager manager;

    @Before
    public void init(){
        manager = new com.heapixLearn.discovery.logic.contact.ContactManager();
        contacts = manager.getAll();
        adapter = new ContactListAdapter(contacts, AppContext.getInstance().getBaseContext());
    }

    @Test
    public void delete(){
        adapter.deleteDataFromList(1);
        adapter.deleteDataFromList(2);
        Assert.assertTrue(contacts.isEmpty());
    }

    @Test
    public void update(){
        Contact contact = new com.heapixLearn.discovery.logic.contact.Contact(2, "newname", 150,
                "newavatar", true);
        adapter.updateDataInList(contact);
        Assert.assertEquals(contacts.get(1).getName(), "newname");
    }


}
