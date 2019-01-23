package com.heapixLearn.discovery.ui.contact;

import java.util.ArrayList;

public interface ContactManager {
    void create(Contact data, Runnable onSuccess, Runnable onFailure);
    void update(Contact data, Runnable onSuccess, Runnable onFailure);
    void delete(int id, Runnable onSuccess, Runnable onFailure);
    Contact getById(int id);
    ArrayList<Contact> getAll();
}
