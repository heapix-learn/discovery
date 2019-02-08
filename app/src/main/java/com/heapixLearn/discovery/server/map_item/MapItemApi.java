package com.heapixLearn.discovery.server.map_item;

import com.heapixLearn.discovery.server.ServerAnswer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MapItemApi {
    @GET("/map-items")
    Call<List<ServerMapItem>> getAll();

    @GET("/map-items/{id}")
    Call<ServerMapItem> getById(@Path("post-id") String post_id);

    @POST("/map-items")
    Call<ServerMapItem> createMapItem(@Body ServerMapItem mapItem);

    @PUT("/map-items/{id}")
    Call<ServerAnswer> updateMapItem(@Path("post-id") String post_id, @Body ServerMapItem serverPost);

    @DELETE("/map-items/{id}")
    Call<ServerAnswer> deleteMapItem(@Path("post-id") String post_id);
}
