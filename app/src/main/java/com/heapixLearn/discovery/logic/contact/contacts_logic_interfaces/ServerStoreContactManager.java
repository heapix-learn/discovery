package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface ServerStoreContactManager {
    LogicContact create(LogicContact data);
    LogicContact update(LogicContact data);
    boolean delete(LogicContact data);
    ArrayList<LogicContact> getAll();
    ArrayList<LogicContact> getFollowings(int id);
}
