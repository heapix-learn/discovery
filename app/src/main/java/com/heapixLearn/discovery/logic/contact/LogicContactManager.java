package com.heapixLearn.discovery.logic.contact;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces.AuthStore;
import com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces.DBStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces.ServerStoreContactManager;
import com.heapixLearn.discovery.logic.contact.contacts_logic_interfaces.UIContactManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LogicContactManager {
    private static String TAG = "!!!LOG!!!";

    static LogicContactManager instance;
    AuthStore store = null;
    DBStoreContactManager dbManager;
    ServerStoreContactManager serverManager;
    UIContactManager uiManager;

    public static LogicContactManager getInstance() {
        if (instance == null) {
            instance = new LogicContactManager();
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


    @SuppressLint("StaticFieldLeak")
    public ArrayList<LogicContact> getAll(Runnable onSuccess, Runnable onFail) {
        Handler handler = new Handler(Looper.getMainLooper());
        ArrayList<LogicContact> users = null;

        try {
            users = new AsyncTask<Void, Void, ArrayList<LogicContact>>() {
                @Override
                protected ArrayList<LogicContact> doInBackground(Void... voids) {
                    if (serverManager.getAll() == null) {
                        handler.post(onFail);
                    } else {
                        handler.post(onSuccess);
                        return serverManager.getAll();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(ArrayList<LogicContact> users) {
                    super.onPostExecute(users);
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users;
    }


    /***
     *
     * @param onSuccess
     * @param onFail
     * @return User followings
     *
     * Get followings from local DB and display it.
     * Then Run new Thread.
     * There we get following list from server.
     * If null is returned -> some kind of error has occurred and onFail() run in UI.
     * Else onSuccess() run in UI and if we have some change on server(for eg your following delete his account),
     * local db updated and UI gets the command to update.
     *
     */
    public ArrayList<LogicContact> getFollowings(Runnable onSuccess, Runnable onFail) {
        int userId = store.getContact().getId();
        ArrayList<LogicContact> followingsFromDB = new ArrayList(dbManager.getFollowings());

        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = () -> {
            ArrayList<LogicContact> followingsFromServer = serverManager.getFollowings(userId);

            if (followingsFromServer == null) {
                handler.post(onFail);
            } else {
                handler.post(onSuccess);

                for (LogicContact contact : followingsFromServer) {
                    if (!followingsFromDB.contains(contact)) {
                        onServerContactDeleted(contact);
                    }

                    if (!(contact.equals(dbManager.getById(contact)))) {
                        onServerContactUpdated(contact);
                    }
                }
            }
        };
        new Thread(runnable).start();
        return followingsFromDB;
    }


    /***
     *
     * @param contacts
     *
     * Every time new posts are loaded into the local db,
     * this method triggers and the owners of the old posts are deleted from db,
     * the users who own new posts are loaded into db.
     */
    public void onNewPostSaved(ArrayList<LogicContact> contacts) {
        Runnable runnable = () -> {
            dbManager.deleteAllExceptFollowings();
            for (LogicContact contact : contacts) {
                dbManager.create(contact);
            }
        };
        new Thread(runnable).start();
    }



    public void onServerContactDeleted(LogicContact contact) {
        dbManager.create(contact);
        uiManager.onContactDeleted(contact);
    }

    public void onServerContactUpdated(LogicContact contact) {
        dbManager.create(contact);
        uiManager.onContactUpdated(contact);
    }
}
