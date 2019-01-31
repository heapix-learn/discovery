package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

import java.util.ArrayList;

public interface DBStorePostManager {
    ArrayList<LogicContact> getContactsFromPost();
}
