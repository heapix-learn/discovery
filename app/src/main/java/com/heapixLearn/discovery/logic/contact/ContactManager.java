package com.heapixLearn.discovery.logic.contact;

import com.heapixLearn.discovery.ui.contact.Contact;

import java.util.ArrayList;

public class ContactManager implements com.heapixLearn.discovery.ui.contact.ContactManager {
    @Override
    public void update(Contact data, Runnable onSuccess, Runnable onFailure) {

    }

    @Override
    public void delete(int id, Runnable onSuccess, Runnable onFailure) {

    }

    @Override
    public Contact getById(int id) {
        ArrayList<Contact> contacts = getAll();
        for(Contact contact : contacts){
            if(contact.getId() == id){
                return contact;
            }
        }
        return null;
    }

    @Override
    public ArrayList<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new com.heapixLearn.discovery.logic.contact.Contact(1, "TestName",
                100, "avatar", true));
        contacts.add(new com.heapixLearn.discovery.logic.contact.Contact(2, "TestName",
                100, "avatar", true));
        return contacts;
    }
}
