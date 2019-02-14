package com.heapixLearn.discovery.ui.post.preview.dummy;

import com.heapixLearn.discovery.ui.post.preview.IPostManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostManager implements IPostManager {
    private List<Post> posts = new ArrayList<>();

    private void initPosts(){
        posts.add(new Post("Post1" , 1,
                new ArrayList<String>(Arrays.asList(
                        "https://storge.pic2.me/c/1360x800/555/57753010826d4.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg",
                        "https://siliconangle.com/files/2013/10/chrome-hacked-story-300x300.jpg"
                )),
                new ArrayList<String>(){},
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
}
