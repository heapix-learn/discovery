package com.heapixLearn.discovery.domain.contact;

import com.heapixLearn.discovery.logic.contact.ViewableContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContactManager;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class DomainServerContactManager implements ServerStoreContactManager {

    @Override
    public FutureTask<ViewableContact> create(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        return null;
    }

    @Override
    public FutureTask<ViewableContact> update(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        return null;
    }

    @Override
    public FutureTask<ViewableContact> delete(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        return null;
    }

    @Override
    public FutureTask<ViewableContact> getById(ViewableContact data) {
        return null;
    }

    @Override
    public FutureTask<ArrayList<ViewableContact>> getAll() {
        return null;
    }
}
