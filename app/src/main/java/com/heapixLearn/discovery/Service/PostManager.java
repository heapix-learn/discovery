package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;
import com.heapixLearn.discovery.Entity.RunnableWithObject;
import com.heapixLearn.discovery.Entity.TypeOfServerError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostManager {
    private static PostManager instance;
    private final int DB_MAX_AMOUNT = 100;
    private final int MIN_RESERVE = 5;
    private ServerPostManagerI serverManager;
    private DBPostManagerI dbManager;
    private ArrayList<Post> allPosts;
    private Thread newPostsThread;
    private Thread thread;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    private int lastIndex;
    private long lastPostTime;

    private PostManager() {
        //TODO init server & DB
        serverManager = null;
        dbManager = null;
        initAllPostsList();
    }

    public static synchronized PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    private void initAllPostsList() {
        allPosts = new ArrayList<>();
        List<Post> dbList = dbManager.getAll();
        if (dbList != null) {
            if (dbList.size() != 0) {
                allPosts.addAll(dbList);
            }
        }
        Collections.sort(allPosts);
        lastIndex = allPosts.size() - 1;
        lastPostTime = allPosts.get(lastIndex).getTime();
    }

    public void getFirstPosts(int amount,
                              RunnableWithObject<List<Post>> onSuccess,
                              RunnableWithObject<TypeOfServerError> onFail) {
        joinAllThreads();

        if (amount <= 0) {
            onSuccess.init(null);
            onSuccess.run();
            return;
        }

        if (lastIndex < amount) {
            loadOlderPostsFromServer(onFail);
            onSuccess.init(getSublist(0, lastIndex));
            onSuccess.run();
            return;
        }

        checkReserve(amount, onFail);
        onSuccess.init(getSublist(0, amount));
        onSuccess.run();
    }

    public void getPosts(Post post, int amount,
                         RunnableWithObject<List<Post>> onSuccess,
                         RunnableWithObject<TypeOfServerError> onFail) {
        joinAllThreads();

        if (checkInputData(post, amount)) {
            onSuccess.init(null);
            onSuccess.run();
            return;
        }

        int start = allPosts.indexOf(post) + 1;
        int stop = start + amount;

        if (lastIndex < stop) {
            loadOlderPostsFromServer(onFail);
            onSuccess.init(getSublist(start, lastIndex));
            onSuccess.run();
            return;
        }

        checkReserve(stop, onFail);
        onSuccess.init(getSublist(start, stop));
        onSuccess.run();
    }

    private boolean checkInputData(Post post, int amount){
        return post == null || amount <= 0 || !allPosts.contains(post);
    }

    private List<Post> getSublist(int start, int stop) {
        return new ArrayList<>(allPosts.subList(start, stop));
    }

    private void checkReserve(int lastRequested, RunnableWithObject<TypeOfServerError> onFail) {
        int reserve = lastIndex - lastRequested;
        if (reserve < MIN_RESERVE) {
            loadOlderPostsFromServer(onFail);
        }
    }

    public void getByUserID(int userId,
                            RunnableWithObject<List<Post>> onSuccess,
                            RunnableWithObject<TypeOfServerError> onFail) {

        executorService.submit(() -> {
            RunnableWithObject<List<Post>> success = new RunnableWithObject<List<Post>>() {
                @Override
                public void run() {
                    onSuccess.init(getObject());
                    onSuccess.run();
                }
            };

            serverManager.getByUserId(userId, success, getOnFailRunnable(onFail));
        });
     /*   new Thread(() -> {
            RunnableWithObject<List<Post>> success = new RunnableWithObject<List<Post>>() {
                @Override
                public void run() {
                    onSuccess.init(getObject());
                    onSuccess.run();
                }
            };

            serverManager.getByUserId(userId, success, getOnFailRunnable(onFail));
        }).start();*/
    }

    public void getByID(int id,
                        RunnableWithObject<Post> onSuccess,
                        RunnableWithObject<TypeOfServerError> onFail) {
        new Thread(() -> {
            RunnableWithObject<Post> success = new RunnableWithObject<Post>() {
                @Override
                public void run() {
                    onSuccess.init(getObject());
                    onSuccess.run();
                }
            };

            serverManager.getById(id, success, getOnFailRunnable(onFail));
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
                        Post newPost = getObject();
                        addPostToDB(newPost);
                        allPosts.add(newPost);
                        Collections.sort(allPosts);
                        lastIndex++;
                        onSuccess.init(post);
                        onSuccess.run();
                }
            };
            serverManager.insert(post, success, getOnFailRunnable(onFail));
        });
        thread.start();
    }

    public void removePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        joinThread(thread);
        thread = new Thread(() -> {
            Runnable success = () -> {
                allPosts.remove(post);
                dbManager.delete(post);
                onSuccess.run();
                lastIndex--;
            };

            serverManager.delete(post, success, getOnFailRunnable(onFail));
        });

        thread.start();
    }

    public void updatePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        joinThread(thread);
        thread = new Thread(() -> {
            Runnable success = () -> {
                int index = allPosts.indexOf(post);
                allPosts.remove(dbManager.getById(post.getId()));
                dbManager.update(post);
                allPosts.add(index, post);
                onSuccess.run();
            };

            serverManager.update(post, success, getOnFailRunnable(onFail));
        });
        thread.start();
    }

    private RunnableWithObject<TypeOfServerError> getOnFailRunnable(
            RunnableWithObject<TypeOfServerError> onFail) {
        return new RunnableWithObject<TypeOfServerError>() {
            @Override
            public void run() {
                onFail.init(getObject());
                onFail.run();
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
        if (dbManager.size() > DB_MAX_AMOUNT) {
            Post p = allPosts.get(lastIndex - DB_MAX_AMOUNT);
            dbManager.delete(p);
        }
        dbManager.insert(post);
    }

    private void loadOlderPostsFromServer(RunnableWithObject<TypeOfServerError> onFail) {
        RunnableWithObject<List<Post>> onSuccess = new RunnableWithObject<List<Post>>() {
            @Override
            public void run() {
                getNewPostsThread(getObject()).start();

                lastPostTime = allPosts.get(lastIndex).getTime();
            }
        };

        serverManager.getPostList(lastPostTime, MIN_RESERVE * 3, onSuccess, getOnFailRunnable(onFail));
    }

    public void loadNewPostsFromServer(Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFail) {
        RunnableWithObject<List<Post>> onSuccessWithObject = new RunnableWithObject<List<Post>>() {
            @Override
            public void run() {
                removePosts();
                getNewPostsThread(getObject()).start();

                lastPostTime = allPosts.get(lastIndex).getTime();

                onSuccess.run();
            }
        };

        serverManager.getPostList(System.currentTimeMillis(), MIN_RESERVE * 3, onSuccessWithObject, getOnFailRunnable(onFail));
    }

    private void removePosts() {
        allPosts.clear();
        dbManager.clear();
    }

    private Thread getNewPostsThread(List<Post> newPosts) {
        joinAllThreads();
        return newPostsThread = new Thread(() -> {
            allPosts.addAll(newPosts);
            Collections.sort(allPosts);
            lastIndex = allPosts.size() - 1;
        });
    }
}