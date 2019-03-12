package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;
import com.heapixLearn.discovery.Entity.RunnableWithObject;
import com.heapixLearn.discovery.Entity.TypeOfServerError;

import java.util.List;

public interface ServerPostManagerI {

    void getPostList(long time, int amount, RunnableWithObject<List<Post>> onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
    void getById(int id, RunnableWithObject<Post> onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
    void getByUserId(int id, RunnableWithObject<List<Post>> onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
    void insert(Post post, RunnableWithObject<Post> onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
    void delete(Post post, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
    void update(Post post, Runnable onSuccess, RunnableWithObject<TypeOfServerError> onFailure);
}
