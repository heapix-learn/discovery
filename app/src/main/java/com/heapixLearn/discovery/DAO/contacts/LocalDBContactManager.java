package com.heapixLearn.discovery.DAO.contacts;

import com.heapixLearn.discovery.App;

import java.util.List;

public class LocalDBContactManager {
    private static LocalDBContactManager instance;

    private ContactDAO contactDAO;
    private Thread thread;

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
        joinThread();
        return contactDAO.getAll();
    }

    public LocalDBContact getById(final int id) {
        joinThread();
        return contactDAO.getById(id);
    }

    public void insert(final LocalDBContact contact) {
        joinThread();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(contact);
            }
        });
        thread.start();
    }

    public void delete(final LocalDBContact contact) {
        joinThread();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete(contact);
            }
        });
        thread.start();
    }

    public void update(final LocalDBContact contact) {
        joinThread();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                contactDAO.update(contact);
            }
        });
        thread.start();
    }

    private void joinThread() {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }
}
