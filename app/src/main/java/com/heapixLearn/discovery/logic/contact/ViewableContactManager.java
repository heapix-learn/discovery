package com.heapixLearn.discovery.logic.contact;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.heapixLearn.discovery.domain.contact.DomainDBContactManager;
import com.heapixLearn.discovery.domain.contact.DomainServerContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.DBStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.ServerStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_domain_interfaces.UIContactManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ViewableContactManager implements UIContactManager {
    private static String TAG = "!!!LOG!!!";
    static ViewableContactManager instance;
    DBStoreContactManager dbManager;
    ServerStoreContactManager serverManager;

    private ViewableContactManager(DBStoreContactManager dbManager, ServerStoreContactManager serverManager) {
        this.dbManager = dbManager;
        this.serverManager = serverManager;
    }

    public ViewableContactManager getInstance() {
        if (instance == null) {
            instance = new ViewableContactManager(new DomainDBContactManager(), new DomainServerContactManager());
        }
        return instance;
    }


    @Override
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

    @Override
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

    @Override
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

    @Override
    public ViewableContact getById(ViewableContact data) {
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

    @Override
    public ArrayList<ViewableContact> getAll() {
        ArrayList<ViewableContact> contactListFromServer = new ArrayList<>();
        ArrayList<ViewableContact> contactListFromDB = new ArrayList<>();

        try {
            FutureTask<ArrayList<ViewableContact>> dbTask = dbManager.getAll();
            contactListFromDB.addAll(dbTask.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getAll from DBContactsManager: " + e);
        }


        Runnable copyNewContactFromServer = () -> {
            try {
                FutureTask<ArrayList<ViewableContact>> serverTask = serverManager.getAll();
                new Thread(serverTask).start();
                contactListFromServer.addAll(serverTask.get());
            } catch (ExecutionException | InterruptedException e) {
                Log.d(TAG, " ViewableContactManager getAll from ServerContactsManager: " + e);
            }

            if (contactListFromServer.size() > contactListFromDB.size()) {
                ArrayList<ViewableContact> contactsToCreateInDB = new ArrayList<>();

                for (ViewableContact contact: contactListFromServer) {
                    if(!(contactListFromDB.contains(contact))) {
                        contactsToCreateInDB.add(contact);
                    }
                }

                for (ViewableContact contact: contactsToCreateInDB) {
                    FutureTask postNewContacts = dbManager.create(contact);
                    new Thread(postNewContacts).start();
                }
            }
            if(contactListFromServer.size() < contactListFromDB.size()) {
                ArrayList<ViewableContact> contactsToDeleteFromDB = new ArrayList<>();

                for (ViewableContact contact: contactListFromDB) {
                    if(!(contactListFromServer.contains(contact))) {
                        contactsToDeleteFromDB.add(contact);
                    }
                }

                for (ViewableContact contact: contactsToDeleteFromDB) {
                    FutureTask postNewContacts = dbManager.delete(contact);
                    new Thread(postNewContacts).start();
                }
            }

        };
        new Thread(copyNewContactFromServer).start();
        return contactListFromDB;
    }
}
