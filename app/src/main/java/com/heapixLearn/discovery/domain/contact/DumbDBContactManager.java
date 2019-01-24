package com.heapixLearn.discovery.domain.contact;

import com.heapixLearn.discovery.logic.contact.LogicContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContactManager;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class DumbDBContactManager implements DBStoreContactManager {


    @Override
    public FutureTask<Void> create(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<Void> update(LogicContact data) {
        return null;
    }

    @Override
    public FutureTask<Void> delete(LogicContact data) {
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

    @Override
    public void onContactCreated(LogicContact contact) {

    }

    @Override
    public void omContactUpDated(LogicContact contact) {

    }

    @Override
    public void onContactUpdated(LogicContact contact) {

    }
}
