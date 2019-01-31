package com.heapixLearn.discovery.logic.contact;

import com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces.DBStoreContact;

public class LogicContact {
    DBStoreContact dbContact;

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


    public boolean isFriend() {
        return dbContact.isFriend();
    }


    @Override
    public boolean equals(Object obj) {
        LogicContact contactToCompare = (LogicContact) obj;
        if ((getId() == contactToCompare.getId()) &&
                (getName().equals(contactToCompare.getName())) &&
                (getNick().equals(contactToCompare.getNick())) &&
                (getPhone().equals(contactToCompare.getPhone())) &&
                (getAvatar().equals(contactToCompare.getAvatar())) &&
                (getEmail().equals(contactToCompare.getEmail())) &&
                (isFriend() == contactToCompare.isFriend())
        ) {
            return true;
        }

        if (this == obj) {
            return true;
        }
        return false;
    }
}
