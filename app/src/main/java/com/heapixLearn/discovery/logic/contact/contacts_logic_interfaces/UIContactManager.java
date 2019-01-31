package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface UIContactManager {
    ArrayList<LogicContact> getAll(Runnable onSuccess, Runnable onFail);
    void onContactDeleted(LogicContact contact);
    void onContactCreated(LogicContact contact);
    void onContactUpdated(LogicContact contact);
    void updateContactList(ArrayList<LogicContact> contacts);
}
