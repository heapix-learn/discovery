package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

public interface DBStoreContact {
    int getId();
    String getName();
    String getNick();
    String getPhone();
    String getAvatar();
    String getEmail();
    boolean isFriend();
}
