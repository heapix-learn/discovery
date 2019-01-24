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

    public static synchronized ViewPostManager getInstance() {
        if (instance == null) {
            instance = new ViewPostManager();
        }
        return instance;
    }

    public void onNewPostsListener(Runnable runnable) {
        newPostsRunnable = runnable;
    }

    private ViewPostManager() {
        serverAdapter = new ServerPostAdapter();
        initDB();
        initAllPostsList();
        checkNewPosts();
    }

    private void initAllPostsList(){
        allPosts = new ArrayList<>();
        allPosts.addAll(dbList);
        Collections.sort(allPosts);
    }

    private void initDB(){
        dbAdapter = new DBPostAdapter();
        dbList = dbAdapter.getAll();
    }

    public List<ViewablePost> getFirst(int amount) {
        allPostsIterator = allPosts.listIterator();
        return getSubList(amount);
    }

    public List<ViewablePost> getNewPosts(int amount) {
        return getFirst(amount);
    }

    public void addPost(ViewablePost post){
        addPostToDB(post);
        allPosts.add(post);

    }

    public List<ViewablePost> getNext(int amount) {
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
                    try {
                        wait(60000);
                    } catch (InterruptedException e) {
                    }
                    getNewPostsFromServer();
                }
            }
        });
    }

    private synchronized void addPostToDB(ViewablePost post){
        if(dbList.size() > 99){
            ViewablePost p = dbList.get(dbList.size()-1);
            dbList.remove(p);
            dbAdapter.delete(p);
        }
        dbList.add(post);
        dbAdapter.insert(post);
    }

    private void getNewPostsFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (serverAdapter.hasNewPosts()) {
                    int[] newIds = serverAdapter.getNewIds();
                    for (int i : newIds) {
                        ViewablePost post = serverAdapter.getById(i);
                        allPosts.add(post);
                        addPostToDB(post);
                    }
                }
            }
        }).start();
        new Thread(newPostsRunnable).start();
    }
}
