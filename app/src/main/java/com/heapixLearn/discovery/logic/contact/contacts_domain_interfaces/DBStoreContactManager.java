package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface DBStoreContactManager {
    Void create(LogicContact data);
    Void update(LogicContact data);
    Void delete(LogicContact data);
    LogicContact getById(LogicContact data);
    ArrayList<LogicContact> getAll();
    void onContactCreated(LogicContact contact);
    void omContactDeleted(LogicContact contact);
    void onContactUpdated(LogicContact contact);
}
