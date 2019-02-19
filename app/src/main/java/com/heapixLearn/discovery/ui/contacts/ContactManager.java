package com.heapixLearn.discovery.ui.contacts;

import java.util.ArrayList;

public interface ContactManager {
    void update(Contact data, Runnable onSuccess, Runnable onFailure);
    void delete(int id, Runnable onSuccess, Runnable onFailure);
    Contact getById(int id);
    ArrayList<Contact> getAll();
}
