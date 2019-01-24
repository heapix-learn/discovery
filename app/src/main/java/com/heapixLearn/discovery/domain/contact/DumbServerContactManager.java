package com.heapixLearn.discovery.domain.contact;

import com.heapixLearn.discovery.logic.contact.LogicContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContactManager;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class DumbServerContactManager implements ServerStoreContactManager {

    @Override
    public FutureTask<LogicContact> create(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<LogicContact> update(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<Boolean> delete(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<LogicContact> getById(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<ArrayList<LogicContact>> getAll() {
        return null;
    }
}
