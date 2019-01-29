package com.heapixLearn.discovery.server;

import com.heapixLearn.discovery.server.contacts.ServerContact;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface UserApi {
    @POST("/users")
    Call<ServerContact> createContact(@Body ServerContact contact);

    @GET("/users")
    Call<List<ServerContact>> readContacts();

    @PUT("/users/{id}")
    Call<ServerAnswer> updateContact(@Path("id") String id, @Body ServerContact serverContact);

    @DELETE("/users/{id}")
    Call<ServerAnswer> deleteContact(@Path("id") String id);

    @GET("/users/{id}")
    Call<ServerContact> getContactById(@Path("id") String id);
}
