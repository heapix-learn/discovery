package com.heapixLearn.discovery.Service;

import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.Entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ViewPostManager {
    private static ViewPostManager instance;
    private ServerPostAdapterI serverAdapter;
    private DBPostAdapterI dbAdapter;
    private List<Post> dbList;
    private ArrayList<Post> allPosts;
    private ListIterator<Post> allPostsIterator;
    private Runnable newPostsRunnable;
    private Thread newPostsThread;
    private Thread addPostThread;
    private Thread removePostThread;
    private Thread updatePostThread;

    private final int DB_MAX_AMOUNT = 100;
    private final int SERVER_REQUEST_TIMEOUT = 60000;

    public static synchronized ViewPostManager getInstance() {
        if (instance == null) {
            instance = new ViewPostManager();
        }
        return instance;
    }

    private ViewPostManager() {
        serverAdapter = null;
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
        dbAdapter = null;
        dbList = dbAdapter.getAll();
    }

    public List<Post> getFirst(int amount) {
        joinAllThreads();
        allPostsIterator = allPosts.listIterator();
        return getSubList(amount);
    }

    public List<Post> getByUserID(int userId) {
        List<Post> desiredPosts = serverAdapter.getByUserId(userId);
        for (Post post : allPosts){
            if(post.getUserId() == userId & !desiredPosts.contains(post)){
                desiredPosts.add(post);
            }
        }
        return desiredPosts;
    }

    public List<Post> getNext(int amount) {
        joinAllThreads();
        for (int i = 0; i < amount; ) {
            Post newPost = serverAdapter.getNext();
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

    private List<Post> getSubList(int amount) {
        List<Post> list = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (allPostsIterator.hasNext()) {
                list.add(allPostsIterator.next());
            } else {
                break;
            }
        }
        return list;
    }

    public void addPost(Post post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            Post newPost = serverAdapter.insert(post);

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

    public void removePost(Post post, Runnable onSuccess, Runnable onFail) {
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

    public void updatePost(Post post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            int index = allPosts.indexOf(post);
            allPosts.remove(dbAdapter.getById(post.getId()));

            Post newPost = serverAdapter.update(post);
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
                    wait(SERVER_REQUEST_TIMEOUT);
                } catch (InterruptedException e) {
                }
            }
        };
        new Thread(runnable).start();
    }

    private void addPostToDB(Post post) {
        if (dbList.size() > DB_MAX_AMOUNT) {
            Post p = dbList.get(0);
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
