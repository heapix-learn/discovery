package com.heapixLearn.discovery.DAO.LocalDBContact;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class LocalDBContactManager {
    private static LocalDBContactManager instance;

    private ContactDAO contactDAO;
    private Thread threadDelete;
    private Thread threadInsert;
    private Thread threadUpdate;

    public static synchronized LocalDBContactManager getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDBContactManager(context);
        }
        return instance;
    }

    private LocalDBContactManager(Context applicationContext) {
        ContactDB db = Room.databaseBuilder(applicationContext, ContactDB.class, "contactDB").build();
        contactDAO = db.getContactDAO();
    }

    public FutureTask<List<LocalDBContact>> getAll() {
        joinAllThreads();
        return new FutureTask<>(new Callable<List<LocalDBContact>>() {
            @Override
            public List<LocalDBContact> call() throws Exception {
                return contactDAO.getAll();
            }
        });
    }

    public FutureTask<LocalDBContact> getById(final int id) {
        joinAllThreads();
        return new FutureTask<>(new Callable<LocalDBContact>() {
            @Override
            public LocalDBContact call() throws Exception {
                return contactDAO.getById(id);
            }
        });
    }

    public void insert(final LocalDBContact contact) {
        joinAllThreads();
        threadInsert = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(contact);
            }
        });
        threadInsert.start();
    }

    public void delete(final LocalDBContact contact) {
        joinAllThreads();
        threadDelete = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });
        threadDelete.start();
    }

    public void update(final LocalDBContact contact) {
        joinAllThreads();
        threadUpdate = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.update(contact);
            }
        });
        threadUpdate.start();
    }

    private void joinThread(Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    private void joinAllThreads() {
        joinThread(threadUpdate);
        joinThread(threadInsert);
        joinThread(threadDelete);
    }
}