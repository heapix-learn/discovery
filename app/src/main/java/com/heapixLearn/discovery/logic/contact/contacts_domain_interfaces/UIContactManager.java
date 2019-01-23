package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.ViewableContact;

public interface UIContactManager {
    void onContactDeleted(ViewableContact contact);
    void onContactCreated(ViewableContact contact);
    void onContactUpdated(ViewableContact contact);
}
