package com.heapixLearn.discovery.logic.contact;

import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContact;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.UIContact;

public class LogicContact {
    DBStoreContact dbContact;
    ServerStoreContact serverContact;
    UIContact uiContact;

    public LogicContact(DBStoreContact dbContact) {
        this.dbContact = dbContact;
    }


    public int getId() {
        return dbContact.getId();
    }


    public String getName() {
        return dbContact.getName();
    }


    public String getNick() {
        return dbContact.getNick();
    }


    public String getPhone() {
        return dbContact.getPhone();
    }


    public String getAvatar() {
        return dbContact.getAvatar();
    }


    public String getEmail() {
        return dbContact.getEmail();
    }


    public Boolean isFriend() {
        return dbContact.isFriend();
    }


    public int getRemoteId() {
        return 0;
    }
}
