package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.ViewableContact;

import java.util.ArrayList;

public interface UIContactManager {
    void create(ViewableContact data, Runnable onSuccess, Runnable onFail);
    void update(ViewableContact data, Runnable onSuccess, Runnable onFail);
    void delete(ViewableContact data, Runnable onSuccess, Runnable onFail);
    ViewableContact getById(ViewableContact data);
    ArrayList<ViewableContact> getAll();
}
