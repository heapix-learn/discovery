package com.heapixLearn.discovery.domain.contact;

import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContact;

public class DumbDBContact implements DBStoreContact {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getNick() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getAvatar() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public Boolean isFriend() {
        return null;
    }

    @Override
    public int getRemoteId() {
        return 0;
    }
}
