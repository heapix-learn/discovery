package com.heapixLearn.discovery.ui.post.preview.dummy;

import com.heapixLearn.discovery.ui.post.preview.IPostManager;
import com.heapixLearn.discovery.ui.post.preview.entity.IPost;
import com.heapixLearn.discovery.ui.post.preview.entity.VideoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostManager implements IPostManager {
    private List<Post> posts = new ArrayList<>();

    private void initPosts(){
        posts.add(new Post("Post1" , 3,
                new ArrayList<String>(Arrays.asList(
                        "https://storge.pic2.me/c/1360x800/555/57753010826d4.jpg",
                        "https://i.imgur.com/DvpvklR.png",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg"
                )),
                new ArrayList<VideoItem>(Arrays.asList(
                        new VideoItem(
                                "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4",
                                "https://i.imgur.com/DvpvklR.png",
                                "1:20"
                        )
                )),
                "Soligorsk",
                150, 25,
                false, true)
        );


    }

    @Override
    public Post getPostById(int id) {
        initPosts();
        return posts.get(0);
    }

    @Override
    public void update(IPost post, Runnable onSuccess, Runnable onFailure) {
        onSuccess.run();
    }

    @Override
    public void openFullScreen(Runnable onSuccess, Runnable onFailure) {
        onSuccess.run();
    }

    @Override
    public void openComments(Runnable onSuccess, Runnable onFailure) {
        onSuccess.run();
    }

    @Override
    public void openInMap(Runnable onSuccess, Runnable onFailure) {
        onSuccess.run();
    }

}
