package com.heapixLearn.discovery.DAO.LocalDBContact;

import com.heapixLearn.discovery.App;

import java.util.List;

public class LocalDBContactManager {
    private static LocalDBContactManager instance;

    private ContactDAO contactDAO;
    private Thread threadDelete;
    private Thread threadInsert;
    private Thread threadUpdate;

    public static synchronized LocalDBContactManager getInstance() {
        if (instance == null) {
            instance = new LocalDBContactManager();
        }
        return instance;
    }

    private LocalDBContactManager() {
        AppDB db = App.getInstance().getDatabase();
        contactDAO = db.getContactDAO();
    }

    public List<LocalDBContact> getAll() {
        joinAllThreads();
        return contactDAO.getAll();
    }

    public LocalDBContact getById(final int id) {
        joinAllThreads();
        return contactDAO.getById(id);
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
