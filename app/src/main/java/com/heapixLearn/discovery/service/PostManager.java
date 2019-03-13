package com.heapixLearn.discovery.service;

import com.heapixLearn.discovery.entity.Post;
import com.heapixLearn.discovery.entity.RunnableWithObject;
import com.heapixLearn.discovery.entity.TypeOfServerError;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostManager {
    private static PostManager instance;
    private final int DB_MAX_AMOUNT = 100;
    private final int MIN_RESERVE = 5;
    private final int SERVER_REQUEST_SIZE = MIN_RESERVE * 3;
    private ServerPostManagerI serverManager;
    private DBPostManagerI dbManager;
    private ExecutorService executorService = Executors.newFixedThreadPool(6);

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
        executorService.submit(() -> {
            List<Post> result;

            if (amount <= 0) {
                result = null;
            } else {
                int maxIndex = dbManager.size() - 1;

                checkReserve(maxIndex, onFail);

                result = dbManager.getList(0, maxIndex);
            }

            onSuccess.init(result).run();
        });
    }

    public void getPosts(Post post, int amount,
                         RunnableWithObject<List<Post>> onSuccess,
                         RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            List<Post> result;

            if (checkInputData(post, amount)) {
                result = null;
            } else {
                int firstPostIndex = dbManager.indexOf(post) + 1;
                int lastPostIndex = firstPostIndex + amount;

                checkReserve(lastPostIndex, onFail);

                result = dbManager.getList(firstPostIndex, lastPostIndex);
            }

            onSuccess.init(result).run();
        });
    }

    private boolean checkInputData(Post post, int amount) {
        return post == null || amount <= 0 || dbManager.getById(post.getId()) == null;
    }

    private void checkReserve(int lastPostIndex, RunnableWithObject<TypeOfServerError> onFail){
        while (!isEnoughPosts(lastPostIndex)) {
            loadOlderPostsFromServer(onFail);
            try {
                wait();
            } catch (InterruptedException e){}
        }
    }

    private boolean isEnoughPosts(int lastRequested) {
        int reserve = dbManager.size() - 1 - lastRequested;
        return reserve >= MIN_RESERVE;
    }

    public void getPostListByUserID(int userId,
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

    public void getPostByID(int id,
                            RunnableWithObject<Post> onSuccess,
                            RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            RunnableWithObject<Post> success = new RunnableWithObject<Post>() {
                @Override
                public void run() {
                    onSuccess.init(getObject()).run();
                }
            };

            serverManager.getById(id, success, getOnFailRunnable(onFail));
        });
    }

    public void addPost(Post post,
                        RunnableWithObject<Post> onSuccess,
                        RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
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
    }

    public void removePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            Runnable success = () -> {
                dbManager.delete(post);
                onSuccess.run();
            };
            serverManager.delete(post, success, getOnFailRunnable(onFail));
        });
    }

    public void updatePost(Post post,
                           Runnable onSuccess,
                           RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            Runnable success = () -> {
                dbManager.update(post);
                onSuccess.run();
            };

            serverManager.update(post, success, getOnFailRunnable(onFail));
        });
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

    private void addPostToDB(Post post) {
        int dbSize = dbManager.size();
        if (dbSize >= DB_MAX_AMOUNT) {
            Post p = dbManager.getByIndex(0);
            dbManager.delete(p);
        }
        dbManager.insert(post);
    }

    private void loadOlderPostsFromServer(RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            RunnableWithObject<List<Post>> onSuccess = new RunnableWithObject<List<Post>>() {
                @Override
                public void run() {
                    addPostListToDb(getObject());
                    notify();
                }
            };
            long lastPostTime = dbManager.getByIndex(dbManager.size() - 1).getTime();
            serverManager.getPostList(lastPostTime, SERVER_REQUEST_SIZE, onSuccess, getOnFailRunnable(onFail));
        });
    }

    public void loadNewPostsFromServer(Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFail) {
        executorService.submit(() -> {
            RunnableWithObject<List<Post>> success = new RunnableWithObject<List<Post>>() {
                @Override
                public void run() {
                    dbManager.clear();
                    addPostListToDb(getObject());

                    onSuccess.run();
                }
            };

            serverManager.getPostList(System.currentTimeMillis(), SERVER_REQUEST_SIZE, success, getOnFailRunnable(onFail));
        });
    }

    private void addPostListToDb(List<Post> newPosts) {
        for (Post post : newPosts) {
            addPostToDB(post);
        }
    }
}