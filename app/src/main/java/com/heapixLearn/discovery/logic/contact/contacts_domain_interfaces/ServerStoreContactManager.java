package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface ServerStoreContactManager {
    LogicContact create(LogicContact data);
    LogicContact update(LogicContact data);
    Boolean delete(LogicContact data);
    LogicContact getById(LogicContact data);
    ArrayList<LogicContact> getAll();
}
