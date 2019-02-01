package com.heapixLearn.discovery.server;

import android.support.annotation.IntRange;

import com.heapixLearn.discovery.server.ServerAnswer;
import com.heapixLearn.discovery.server.post.ServerPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {
    @POST("/posts")
    Call<ServerPost> createPost(@Body ServerPost post);
    //TODO : partial
    @GET("/posts")
    Call<List<ServerPost>> readPartOfPosts();

    @PATCH("/posts/{id}")
    Call<ServerAnswer> updatePost(@Path("id") String id, @Body ServerPost serverPost);

    @DELETE("/posts/{id}")
    Call<ServerAnswer> deletePost(@Path("id") String id);

    @GET("/posts/{id}")
    Call<ServerPost> getPostById(@Path("id") String id);

    @POST("/posts")
    Call<ServerPost> getNext(@Body ServerPost post);

    @POST("/posts")
    Call<ServerAnswer> hasNewPost(@Body ServerPost post);
}
