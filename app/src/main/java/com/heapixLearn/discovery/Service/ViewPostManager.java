package com.heapixLearn.discovery.Service;

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
        if (newPostsThread != null) {
            try {
                newPostsThread.join();
            } catch (InterruptedException e) {
            }
        }
        allPostsIterator = allPosts.listIterator();
        return getSubList(amount);
    }

    public void addPost(ViewablePost post) {
        ViewablePost newPost = serverAdapter.insert(post);
        newPost = addPostToDB(newPost);
        allPosts.add(newPost);
    }

    public void removePost(ViewablePost post){
        allPosts.remove(post);
        dbAdapter.delete(post);
        serverAdapter.delete(post);
    }

    public void updatePost(ViewablePost post){
        serverAdapter.update(post);
        dbAdapter.update(post);
    }

    public List<ViewablePost> getNext(int amount) {
        for(int i = 0; i< amount; i++) {
            ViewablePost newPost = serverAdapter.getNext();
            if(!allPosts.contains(newPost)) {
                newPost = addPostToDB(newPost);
                allPosts.add(newPost);
            }
        }
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

    private void checkNewPosts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    getNewPostsFromServer();
                    try {
                        wait(60000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private ViewablePost addPostToDB(ViewablePost post) {
        if (dbList.size() > DB_MAX_AMOUNT) {
            ViewablePost p = dbList.get(DB_MAX_AMOUNT);
            dbList.remove(p);
            dbAdapter.delete(p);
        }
        ViewablePost newPost = dbAdapter.insert(post);
        dbList.add(newPost);
        return newPost;
    }

    private void getNewPostsFromServer() {
        newPostsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (serverAdapter.hasNewPosts()) {
                   allPosts.addAll(serverAdapter.getFirst(20));
                   Collections.sort(allPosts);
                   allPostsIterator = allPosts.listIterator(allPostsIterator.nextIndex()+20);
                }
            }
        });
        newPostsThread.start();
        new Thread(newPostsRunnable).start();
    }
}
