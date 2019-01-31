package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.LogicContact;

public interface UIContactManager {
    void onContactDeleted(LogicContact contact);
    void onContactCreated(LogicContact contact);
    void onContactUpdated(LogicContact contact);
}
