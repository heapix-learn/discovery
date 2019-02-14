package com.heapixLearn.discovery.Service;

import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.Entity.ViewablePost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ViewPostManager {
    private static ViewPostManager instance;
    private ServerPostAdapterI serverAdapter;
    private DBPostAdapterI dbAdapter;
    private List<ViewablePost> dbList;
    private ArrayList<ViewablePost> allPosts;
    private ListIterator<ViewablePost> allPostsIterator;
    private Runnable newPostsRunnable;
    private Thread newPostsThread;
    private Thread addPostThread;
    private Thread removePostThread;
    private Thread updatePostThread;

    private final int DB_MAX_AMOUNT = 100;

    public static synchronized ViewPostManager getInstance() {
        if (instance == null) {
            instance = new ViewPostManager();
        }
        return instance;
    }

    private ViewPostManager() {
        serverAdapter = new ServerPostAdapter();
        initDB();
        initAllPostsList();
        checkNewPosts();
    }

    public void onNewPostsListener(Runnable runnable) {
        newPostsRunnable = runnable;
    }

    private void initAllPostsList() {
        allPosts = new ArrayList<>();
        allPosts.addAll(dbList);
        Collections.sort(allPosts);
    }

    private void initDB() {
        dbAdapter = new DBPostAdapter();
        dbList = dbAdapter.getAll();
    }

    public List<ViewablePost> getFirst(int amount) {
        joinAllThreads();
        if (newPostsThread != null) {
            try {
                newPostsThread.join();
            } catch (InterruptedException e) {
            }
        }
        allPostsIterator = allPosts.listIterator();
        return getSubList(amount);
    }

    public List<ViewablePost> getByUserID(int userId) {
        List<ViewablePost> desiredPosts = serverAdapter.getByUserId(userId);
        for (ViewablePost post : allPosts){
            if(post.getUserId() == userId & !desiredPosts.contains(post)){
                desiredPosts.add(post);
            }
        }
        return desiredPosts;
    }

    public void addPost(ViewablePost post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            ViewablePost newPost = serverAdapter.insert(post);

            if (newPost == null) {
                handler.post(onFail);
            } else {
                addPostToDB(newPost);
                allPosts.add(newPost);
                Collections.sort(allPosts);
                allPostsIterator = allPosts.listIterator(allPostsIterator.nextIndex() + 1);
                handler.post(onSuccess);
            }
        };
        addPostThread = new Thread(runnable);
        addPostThread.start();
    }

    public void removePost(ViewablePost post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            if (serverAdapter.delete(post)) {
                allPosts.remove(post);
                dbAdapter.delete(post);
                handler.post(onSuccess);
            } else {
                handler.post(onFail);
            }
        };
        removePostThread = new Thread(runnable);
        removePostThread.start();
    }

    public void updatePost(ViewablePost post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            int index = allPosts.indexOf(post);
            allPosts.remove(dbAdapter.getById(post.getId()));

            ViewablePost newPost = serverAdapter.update(post);
            if (newPost == null) {
                handler.post(onFail);
            } else {
                dbAdapter.update(post);
                allPosts.add(index, newPost);
                handler.post(onSuccess);
            }

        };
        updatePostThread = new Thread(runnable);
        updatePostThread.start();
    }

    public List<ViewablePost> getNext(int amount) {
        joinAllThreads();
        for (int i = 0; i < amount; ) {
            ViewablePost newPost = serverAdapter.getNext();
            if (!allPosts.contains(newPost)) {
                i++;
                addPostToDB(newPost);
                allPosts.add(newPost);
            }
        }
        Collections.sort(allPosts);
        allPostsIterator = allPosts.listIterator(allPostsIterator.nextIndex());
        return getSubList(amount);
    }

    private List<ViewablePost> getSubList(int amount) {
        List<ViewablePost> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (allPostsIterator.hasNext()) {
                list.add(allPostsIterator.next());
            } else {
                break;
            }
        }
        return list;
    }

    private void joinAllThreads() {
        joinThread(newPostsThread);
        joinThread(updatePostThread);
        joinThread(removePostThread);
        joinThread(addPostThread);
    }

    private void joinThread(Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    private void checkNewPosts() {
        Runnable runnable = () -> {
            while (true) {
                getNewPostsFromServer();
                try {
                    wait(60000);
                } catch (InterruptedException e) {
                }
            }
        };
        new Thread(runnable).start();
    }

    private void addPostToDB(ViewablePost post) {
        if (dbList.size() > DB_MAX_AMOUNT) {
            ViewablePost p = dbList.get(0);
            dbList.remove(p);
            dbAdapter.delete(p);
        }
        dbAdapter.insert(post);
        dbList.add(post);
    }

    private void getNewPostsFromServer() {
        Runnable runnable = () -> {
            if (serverAdapter.hasNewPosts()) {
                Handler handler = new Handler(Looper.getMainLooper());

                allPosts.addAll(serverAdapter.getFirst(20));
                Collections.sort(allPosts);
                allPostsIterator = allPosts.listIterator(allPostsIterator.nextIndex() + 20);

                handler.post(newPostsRunnable);
            }
        };
        newPostsThread = new Thread(runnable);
        newPostsThread.start();
    }
}
