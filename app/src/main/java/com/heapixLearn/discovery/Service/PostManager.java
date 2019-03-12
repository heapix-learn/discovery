package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;
import com.heapixLearn.discovery.Entity.RunnableWithObject;
import com.heapixLearn.discovery.Entity.TypeOfServerError;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostManager {
    private static PostManager instance;
    private final int DB_MAX_AMOUNT = 100;
    private final int MIN_RESERVE = 5;
    private ServerPostManagerI serverManager;
    private DBPostManagerI dbManager;
    private Thread newPostsThread;
    private Thread thread;
    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private PostManager() {
        //TODO init server & DB
        serverManager = null;
        dbManager = null;
    }

    public static synchronized PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    public void getFirstPosts(int amount,
                              RunnableWithObject<List<Post>> onSuccess,
                              RunnableWithObject<TypeOfServerError> onFail) {
        joinAllThreads();

        if (amount <= 0) {
            onSuccess.init(null).run();
            return;
        }

        int lastIndex = dbManager.size() - 1;

        if (lastIndex < amount) {
            loadOlderPostsFromServer(onFail);
            onSuccess.init(dbManager.getList(0, lastIndex)).run();
            return;
        }

        checkReserve(amount, onFail);
        onSuccess.init(dbManager.getList(0, amount)).run();
    }

    public void getPosts(Post post, int amount,
                         RunnableWithObject<List<Post>> onSuccess,
                         RunnableWithObject<TypeOfServerError> onFail) {
        joinAllThreads();

        if (checkInputData(post, amount)) {
            onSuccess.init(null).run();
            return;
        }

        int start = dbManager.indexOf(post) + 1;
        int stop = start + amount;
        int lastIndex = dbManager.size() - 1;

        if (lastIndex < stop) {
            loadOlderPostsFromServer(onFail);
            onSuccess.init(dbManager.getList(start, lastIndex)).run();
            return;
        }

        checkReserve(stop, onFail);
        onSuccess.init(dbManager.getList(start, stop)).run();
    }

    private boolean checkInputData(Post post, int amount) {
        return post == null || amount <= 0 || dbManager.getById(post.getId()) == null;
    }

    private void checkReserve(int lastRequested, RunnableWithObject<TypeOfServerError> onFail) {
        int reserve = dbManager.size() - 1 - lastRequested;
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
                    onSuccess.init(getObject()).run();
                }
            };

            serverManager.getByUserId(userId, success, getOnFailRunnable(onFail));
        });
    }

    public void getByID(int id,
                        RunnableWithObject<Post> onSuccess,
                        RunnableWithObject<TypeOfServerError> onFail) {
        new Thread(() -> {
            RunnableWithObject<Post> success = new RunnableWithObject<Post>() {
                @Override
                public void run() {
                    onSuccess.init(getObject()).run();
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
                    onSuccess.init(post).run();
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
                dbManager.delete(post);
                onSuccess.run();
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
                dbManager.update(post);
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
                onFail.init(getObject()).run();
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
        int dbSize = dbManager.size();
        if (dbSize >= DB_MAX_AMOUNT) {
            Post p = dbManager.getByIndex(dbSize - DB_MAX_AMOUNT);
            dbManager.delete(p);
        }
        dbManager.insert(post);
    }

    private void loadOlderPostsFromServer(RunnableWithObject<TypeOfServerError> onFail) {
        RunnableWithObject<List<Post>> onSuccess = new RunnableWithObject<List<Post>>() {
            @Override
            public void run() {
                getNewPostsThread(getObject()).start();
            }
        };

        long lastPostTime = dbManager.getByIndex(dbManager.size() - 1).getTime();
        serverManager.getPostList(lastPostTime, MIN_RESERVE * 3, onSuccess, getOnFailRunnable(onFail));
    }

    public void loadNewPostsFromServer(Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFail) {
        RunnableWithObject<List<Post>> success = new RunnableWithObject<List<Post>>() {
            @Override
            public void run() {
                dbManager.clear();
                getNewPostsThread(getObject()).start();

                onSuccess.run();
            }
        };

        serverManager.getPostList(System.currentTimeMillis(), MIN_RESERVE * 3, success, getOnFailRunnable(onFail));
    }

    private Thread getNewPostsThread(List<Post> newPosts) {
        joinAllThreads();
        return newPostsThread = new Thread(() -> {
            for (Post post : newPosts) {
                addPostToDB(post);
            }
        });
    }
}