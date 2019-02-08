package com.heapixLearn.discovery.logic.profile.service;

import android.os.Handler;
import android.os.Looper;

import com.heapixLearn.discovery.logic.profile.entity.Profile;
import com.heapixLearn.discovery.logic.profile.entity.UserPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class ProfileManager {
    private static ProfileManager instance = new ProfileManager();
    private ServerProfileAdapter serverProfileAdapter;
    private DBProfileAdapter dbProfileAdapter;
    private ArrayList<UserPost> postList;
    private List<UserPost> dbPostList;
    private ListIterator<UserPost> iterator;
    private Profile profile;
    private AuthStore authStore;

    private Runnable newPostsRunnable;

    private Thread newPostThread;
    private Thread editPostThread;
    private Thread deletePostThread;

    private ProfileManager(){
        serverProfileAdapter = new com.heapixLearn.discovery.logic.profile.dummy.ServerProfileAdapter();
        //TODO : init userID here
        authStore = new com.heapixLearn.discovery.logic.profile.dummy.AuthStore();
        profile = serverProfileAdapter.getUserByToken(authStore.getToken());
        initDB();
        initPostList();
        checkNewPosts();

    }

    Comparator comparator = (Comparator<UserPost>) (o1, o2) ->
            o1.getDate().compareTo(o2.getDate());

    public static ProfileManager getInstance(){
        return instance;
    }

    public void onNewPostsListener(Runnable runnable) {
        newPostsRunnable = runnable;
    }

    private void initDB(){
        dbProfileAdapter = new com.heapixLearn.discovery.logic.profile.dummy.DBProfileAdapter();
        dbPostList = dbProfileAdapter.getAllByUserId(profile.getId());
    }

    private void initPostList(){
        postList = new ArrayList<>();
        postList.addAll(dbPostList);
        Collections.sort(postList, comparator);
    }

    private void checkNewPosts(){
        getNewPostsFromServer();
    }

    private void getNewPostsFromServer(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                List<UserPost> posts = serverProfileAdapter.getAllByUserId(profile.getId());
                for(UserPost post : posts){
                    addPostToDB(post);
                    postList.add(post);
                }
                Collections.sort(postList, comparator);
                iterator = postList.listIterator(iterator.nextIndex() + posts.size());
                handler.post(newPostsRunnable);
            }
        };
        newPostThread = new Thread(runnable);
        newPostThread.start();
    }

    private void addPostToDB(UserPost post){
        dbProfileAdapter.insert(post);
        dbPostList.add(post);
    }

    private void deletePost(UserPost post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            if (serverProfileAdapter.delete(post.getId())) {
                postList.remove(post);
                dbProfileAdapter.delete(post);
                handler.post(onSuccess);
            } else {
                handler.post(onFail);
            }
        };
        deletePostThread = new Thread(runnable);
        deletePostThread.start();
    }

    public void editPost(UserPost post, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            Handler handler = new Handler(Looper.getMainLooper());
            int index = postList.indexOf(post);
            postList.remove(dbProfileAdapter.getById(post.getId()));

            UserPost newPost = serverProfileAdapter.update(post);
            if (newPost == null) {
                handler.post(onFail);
            } else {
                dbProfileAdapter.update(post);
                postList.add(index, newPost);
                handler.post(onSuccess);
            }

        };
        editPostThread = new Thread(runnable);
        editPostThread.start();
    }

    public String getName(){
        return profile.getName();
    }

    public String getUsername(){
        return profile.getUsername();
    }

    public String getAvatar(){
        return profile.getAvatar();
    }

    public int getFollowers(){
        return profile.getFollowers();
    }
}
