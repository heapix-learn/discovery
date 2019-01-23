package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.ViewableContact;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public interface DBStoreContactManager {
    FutureTask<Void> create(ViewableContact data);
    FutureTask<Void> update(ViewableContact data);
    FutureTask<Void> delete(ViewableContact data);
    FutureTask<ViewableContact> getById(ViewableContact data);
    FutureTask<ArrayList<ViewableContact>> getAll();
    void onContactCreated(ViewableContact contact);
    void omContactUpDated(ViewableContact contact);
    void onContactUpdated(ViewableContact contact);
}
