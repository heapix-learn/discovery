package com.heapixLearn.discovery.logic.contact;

import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.UIContact;

public class ViewableContact implements UIContact {
    DBStoreContact dbContact;
    ServerStoreContact serverContact;
    UIContact uiContact;

    public ViewableContact(DBStoreContact dbContact) {
        this.dbContact = dbContact;
    }


    @Override
    public int getId() {
        return dbContact.getId();
    }

    @Override
    public String getName() {
        return dbContact.getName();
    }

    @Override
    public String getNick() {
        return dbContact.getNick();
    }

    @Override
    public String getPhone() {
        return dbContact.getPhone();
    }

    @Override
    public String getAvatar() {
        return dbContact.getAvatar();
    }

    @Override
    public String getEmail() {
        return dbContact.getEmail();
    }

    @Override
    public Boolean isFriend() {
        return dbContact.isFriend();
    }

    @Override
    public int getRemoteId() {
        return 0;
    }
}
