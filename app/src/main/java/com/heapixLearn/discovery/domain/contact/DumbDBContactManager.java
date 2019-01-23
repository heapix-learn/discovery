package com.heapixLearn.discovery.domain.contact;

import com.heapixLearn.discovery.logic.contact.ViewableContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContactManager;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;

public class DumbDBContactManager implements DBStoreContactManager {


    @Override
    public FutureTask<Void> create(ViewableContact data) {
        return null;
    }

    @Override
    public FutureTask<Void> update(ViewableContact data) {
        return null;
    }

    @Override
    public FutureTask<Void> delete(ViewableContact data) {
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

    @Override
    public void onContactCreated(ViewableContact contact) {

    }

    @Override
    public void omContactUpDated(ViewableContact contact) {

    }

    @Override
    public void onContactUpdated(ViewableContact contact) {

    }
}
