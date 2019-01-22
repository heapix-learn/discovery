package com.heapixLearn.discovery.server;

import com.heapixLearn.discovery.server.contacts.ServerContact;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserApi {

    @POST("/create-contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> createContact(@Body ServerContact serverContact, @Query("access_token")  String access_token);

    @POST("/read-contacts")
    @Headers({"Content-Type: application/json"})
    Call<List<ServerContact>> readContacts(@Query("access_token") String access_token);

    @POST("/update-contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> updateContact(@Body ServerContact serverContact, @Query("access_token")  String access_token);

    @POST("/delete-contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> deleteContact(@Body ServerContact serverContact, @Query("access_token")  String access_token);
}
