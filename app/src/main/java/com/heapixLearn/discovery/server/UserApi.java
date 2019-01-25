package com.heapixLearn.discovery.server;

import com.heapixLearn.discovery.server.contacts.ServerContact;

import java.lang.reflect.Array;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {

    @POST("/users")
    @Headers({"Content-Type: application/json"})
    Call<ServerContact> createContact(@Body ServerContact contact, @Header("Authorization") String token);

    @GET("/users")
    @Headers({"Content-Type: application/json"})
    Call<List<ServerContact>> readContacts(@Header("Authorization") String token);

    @PATCH("/users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> updateContact(@Path("id") String id, @Body ServerContact serverContact, @Header("Authorization") String token);

    @DELETE("/users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> deleteContact(@Path("id") String id, @Header("Authorization") String token);

    @GET("/users/{id}")
    @Headers({"Content-Type: application/json"})
    Call<ServerContact> getContactById(@Path("id") String id, @Header("Authorization") String token);

}
