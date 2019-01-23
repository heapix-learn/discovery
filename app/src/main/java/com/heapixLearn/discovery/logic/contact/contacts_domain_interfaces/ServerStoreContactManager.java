package com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces;

import com.heapixLearn.discovery.logic.contact.ViewableContact;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public interface ServerStoreContactManager {
    FutureTask<ViewableContact> create(ViewableContact data);
    FutureTask<ViewableContact> update(ViewableContact data);
    FutureTask<Boolean> delete(ViewableContact data);
    FutureTask<ViewableContact> getById(ViewableContact data);
    FutureTask<ArrayList<ViewableContact>> getAll();
}
