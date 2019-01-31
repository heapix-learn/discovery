package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface DBStoreContactManager {
    void create(LogicContact data);
    void update(LogicContact data);
    void delete(LogicContact data);
    LogicContact getById(LogicContact data);
    ArrayList<LogicContact> getFollowings();
    void deleteAllExceptFollowings();
}
