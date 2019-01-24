package com.heapixLearn.discovery.logic.contact;

import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.UIContactManager;

import java.util.ArrayList;

public class LogicContactManager {
    private static String TAG = "!!!LOG!!!";
    static LogicContactManager instance;
    DBStoreContactManager dbManager;
    ServerStoreContactManager serverManager;
    UIContactManager uiManager;


    private LogicContactManager(DBStoreContactManager dbManager, ServerStoreContactManager serverManager, UIContactManager uiManager) {
        this.dbManager = dbManager;
        this.serverManager = serverManager;
        this.uiManager = uiManager;
    }

    public LogicContactManager getInstance(DBStoreContactManager dbManager, ServerStoreContactManager serverManager, UIContactManager uiManager) {
        if (instance == null) {
            instance = new LogicContactManager(dbManager, serverManager, uiManager);
        }
        return instance;
    }

    public void create(LogicContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            LogicContact serverContact = serverManager.create(data);
            Handler handler = new Handler(Looper.getMainLooper());

            if (serverContact == null) {
                handler.post(onFail);
            } else {
                dbManager.create(serverContact);
                handler.post(onSuccess);
            }
        };
        new Thread(runnable).start();
    }

    public void update(LogicContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            LogicContact updateServerContact = serverManager.update(data);
            Handler handler = new Handler(Looper.getMainLooper());

            if (updateServerContact == null) {
                handler.post(onFail);
            } else {
                dbManager.update(updateServerContact);
                handler.post(onSuccess);
            }
        };
        new Thread(runnable).start();
    }

    public void delete(LogicContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Boolean deleteFromServer = serverManager.delete(data);
            Handler handler = new Handler(Looper.getMainLooper());

            if (deleteFromServer == null) {
                handler.post(onFail);
            } else {
                dbManager.delete(data);
                handler.post(onSuccess);
            }
        };
        new Thread(runnable).start();
    }


    public LogicContact getById(LogicContact data) {
        return dbManager.getById(data);
    }


    public ArrayList<LogicContact> getAll() {
        return dbManager.getAll();
    }


    public void onServerContactCreated(LogicContact contact) {
            dbManager.onContactCreated(contact);
            uiManager.onContactCreated(contact);
    }

    public void onServerContactDelete(LogicContact contact) {
        dbManager.omContactDeleted(contact);
        uiManager.onContactDeleted(contact);
    }

    public void onServerContactUpdated(LogicContact contact) {
        dbManager.onContactUpdated(contact);
        uiManager.onContactUpdated(contact);
    }
}
