package com.heapixLearn.discovery.ui.post.preview;

import com.heapixLearn.discovery.ui.post.preview.dummy.Post;
import com.heapixLearn.discovery.ui.post.preview.entity.IPost;

public interface IPostManager {
    Post getPostById(int id);
    void update(IPost post, Runnable onSuccess, Runnable onFailure);
    void openFullScreen(Runnable onSuccess, Runnable onFailure);
    void openComments(Runnable onSuccess, Runnable onFailure);
    void openInMap(Runnable onSuccess, Runnable onFailure);
}
