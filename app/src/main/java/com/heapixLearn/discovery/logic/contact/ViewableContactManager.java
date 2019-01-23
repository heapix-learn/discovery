package com.heapixLearn.discovery.logic.contact;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.heapixLearn.discovery.domain.contact.DumbDBContactManager;
import com.heapixLearn.discovery.domain.contact.DumbServerContactManager;
import com.heapixLearn.discovery.domain.contact.DumbUIContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContactManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ViewableContactManager {
    private static String TAG = "!!!LOG!!!";
    static ViewableContactManager instance;
    DBStoreContactManager dbManager;
    ServerStoreContactManager serverManager;
    DumbUIContactManager dumbUIContactManager = new DumbUIContactManager();
    DumbDBContactManager dumbDBContactManager = new DumbDBContactManager();
    DumbServerContactManager dumbServerContactManager = new DumbServerContactManager();



    private ViewableContactManager(DBStoreContactManager dbManager, ServerStoreContactManager serverManager) {
        this.dbManager = dbManager;
        this.serverManager = serverManager;
    }

    public ViewableContactManager getInstance() {
        if (instance == null) {
            instance = new ViewableContactManager(dumbDBContactManager, dumbServerContactManager);
        }
        return instance;
    }

    public void create(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            FutureTask<ViewableContact> createServerContact = serverManager.create(data);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(createServerContact).start();

            try {
                if(createServerContact.get() == null) {
                    handler.post(onFail);
                } else {
                    FutureTask createSqlContact = dbManager.create(createServerContact.get());
                    new Thread(createSqlContact).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }

    public void update(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            FutureTask<ViewableContact> updateServer = serverManager.update(data);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(updateServer).start();

            try {
                if(updateServer.get() == null) {
                    handler.post(onFail);
                } else {
                    FutureTask updateSql = dbManager.update(updateServer.get());
                    new Thread(updateSql).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }

    public void delete(ViewableContact data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            FutureTask<Boolean> deleteFromServer = serverManager.delete(data);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(deleteFromServer).start();

            try {
                if (!deleteFromServer.get()) {
                    handler.post(onFail);
                } else {
                    FutureTask deleteFromSql = dbManager.delete(data);
                    new Thread(deleteFromSql).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }


    public ViewableContact getByDBId(ViewableContact data) {
        FutureTask<ViewableContact> getContact = dbManager.getById(data);
        ViewableContact contact = null;
        new Thread(getContact).start();
        try {
            contact = getContact.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getContactById from DB: " + e);
        }
        return contact;
    }

    public ViewableContact getByServerId(ViewableContact data) {
        FutureTask<ViewableContact> getContact = serverManager.getById(data);
        ViewableContact contact = null;
        new Thread(getContact).start();
        try {
            contact = getContact.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getContactById from SERVER: " + e);
        }
        return contact;
    }


    public ArrayList<ViewableContact> getAll() {
        ArrayList<ViewableContact> contactListFromDB = new ArrayList<>();

        try {
            FutureTask<ArrayList<ViewableContact>> dbTask = dbManager.getAll();
            contactListFromDB.addAll(dbTask.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getAll from DBContactsManager: " + e);
        }
        return contactListFromDB;
    }


    public void onServerContactCreated(ViewableContact contact) {
        dumbDBContactManager.onContactCreated(contact);
        dumbUIContactManager.onContactCreated(contact);
    }

    public void onServerContactDelete(ViewableContact contact) {
        dumbUIContactManager.onContactDeleted(contact);
        dumbUIContactManager.onContactDeleted(contact);
    }

    public void onServerContactUpdated(ViewableContact contact) {
        dumbUIContactManager.onContactUpdated(contact);
        dumbUIContactManager.onContactUpdated(contact);
    }

}
