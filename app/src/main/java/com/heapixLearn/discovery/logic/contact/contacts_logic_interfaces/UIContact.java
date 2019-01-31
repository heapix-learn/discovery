package com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces;

public interface UIContact {
    int getId();
    String getName();
    String getNick();
    String getPhone();
    String getAvatar();
    String getEmail();
    boolean isFriend();
    int getRemoteId();
}
