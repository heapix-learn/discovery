package com.heapixLearn.discovery.Service;

import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.Entity.Post;
import com.heapixLearn.discovery.Entity.RunnableWithObject;
import com.heapixLearn.discovery.Entity.TypeOfServerError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ViewPostManager {
    private static ViewPostManager instance;
    private final int DB_MAX_AMOUNT = 100;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private ServerPostAdapterI serverAdapter;
    private DBPostAdapterI dbAdapter;
    private List<Post> dbList;
    private ArrayList<Post> allPosts;
    private ListIterator<Post> allPostsIterator;
    private Thread newPostsThread;
    private Thread thread;

    private ViewPostManager() {
        //TODO init server
        serverAdapter = null;
        initDB();
        initAllPostsList();
    }

    public static synchronized ViewPostManager getInstance() {
        if (instance == null) {
            instance = new ViewPostManager();
        }
        return instance;
    }

    private void initAllPostsList() {
        allPosts = new ArrayList<>();
        if (dbList != null) {
            if (dbList.size() != 0) {
                allPosts.addAll(dbList);
            }
        }
        Collections.sort(allPosts);
    }

    private void initDB() {
        //TODO init DB
        dbAdapter = null;
        dbList = dbAdapter.getAll();
    }

    public List<Post> getFirstPosts(int amount){
        joinAllThreads();
        int lastIndex = allPosts.size() - 1;

        if(amount <= 0){
            return null;
        }

        if (lastIndex < amount) {
            return getSublist(0, lastIndex);
        }

        return getSublist(0, amount);
    }

    public List<Post> getPosts(Post post, int amount) {
        joinAllThreads();
        int lastIndex = allPosts.size() - 1;

        if (lastIndex < 0 || post == null || amount <= 0 || !allPosts.contains(post)) {
            return null;
        }
        int start = allPosts.indexOf(post) + 1;
        int stop = start + amount;

        if (lastIndex < stop) {
            return getSublist(start, lastIndex);
        }

        return getSublist(start, stop);
    }

    private List<Post> getSublist(int start, int stop) {
        List<Post> list = new ArrayList<>();
        for (int i = start; i <= stop; i++) {
            list.add(allPosts.get(i));
        }
        return list;
    }

    public void getByUserID(int userId,
                            RunnableWithObject<List<Post>> onSuccess,
                            RunnableWithObject<TypeOfServerError> onFail) {
        new Thread(() -> {
            RunnableWithObject<List<Post>> success = new RunnableWithObject<List<Post>>() {
                @Override
                public void run() {
                    new Thread(() -> {
                        onSuccess.init(getObject());
                        handler.post(onSuccess);
                    }).start();
                }
            };

            serverAdapter.getByUserId(userId, success, getOnFailRunnable(onFail));
        }).start();
    }

    public void addPost(Post post,
                        RunnableWithObject<Post> onSuccess,
                        RunnableWithObject<TypeOfServerError> onFail) {
        joinThread(thread);
        thread = new Thread(() -> {
            RunnableWithObject<Post> success = new RunnableWithObject<Post>() {
                @Override
                public void run() {
                    new Thread(() -> {
                        Post newPost = getObject();
                        addPostToDB(newPost);
                        allPosts.add(newPost);
                        Collections.sort(allPosts);
                        allPostsIterator = allPosts.listIterator(allPostsIterator.nextIndex() + 1);
                        onSuccess.init(post);
                        handler.post(onSuccess);
                    }).start();
                }
            };
            serverAdapter.insert(post, success, getOnFailRunnable(onFail));
        });
        thread.start();
    }

    public void removePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        joinThread(thread);
        thread = new Thread(() -> {
            Runnable success = () -> new Thread(() -> {
                allPosts.remove(post);
                dbAdapter.delete(post);
                handler.post(onSuccess);
            }).start();

            serverAdapter.delete(post, success, getOnFailRunnable(onFail));

        });

        thread.start();
    }

    public void updatePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        joinThread(thread);
        thread = new Thread(() -> {
            Runnable success = () -> new Thread(() -> {
                int index = allPosts.indexOf(post);
                allPosts.remove(dbAdapter.getById(post.getId()));
                dbAdapter.update(post);
                allPosts.add(index, post);
                handler.post(onSuccess);
            }).start();

            serverAdapter.update(post, success, getOnFailRunnable(onFail));
        });
        thread.start();
    }

    private RunnableWithObject<TypeOfServerError> getOnFailRunnable(
            RunnableWithObject<TypeOfServerError> onFail) {
        return new RunnableWithObject<TypeOfServerError>() {
            @Override
            public void run() {
                onFail.init(getObject());
                handler.post(onFail);
            }
        };
    }

    private void joinAllThreads() {
        joinThread(newPostsThread);
        joinThread(thread);
    }

    private void joinThread(Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
        }
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
        newPostsThread = new Thread(() ->
                serverAdapter.hasNewPosts(() -> {

                }, null));
        newPostsThread.start();
    }
}
