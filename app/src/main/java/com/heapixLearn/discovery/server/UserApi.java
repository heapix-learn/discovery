package com.heapixLearn.discovery.server;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.heapixLearn.discovery.logic.authorization.AuthUserInfo;
import com.heapixLearn.discovery.logic.authorization.VerificationData;
import com.heapixLearn.discovery.User;
import com.heapixLearn.discovery.server.contacts.ServerContact;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserApi {

    @POST("/contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> createContact(@Body ServerContact serverContact, @Header("Authorization") String token);




    @POST("/read-contacts")
    @Headers({"Content-Type: application/json"})
    Call<List<ServerContact>> readContacts(@Query("access_token") String access_token);

    @POST("/update-contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> updateContact(@Body ServerContact serverContact, @Query("access_token")  String access_token);

    @POST("/delete-contact")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> deleteContact(@Body ServerContact serverContact, @Query("access_token")  String access_token);

    @POST("/signin")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkLogin(@Body AuthUserInfo info);

    @POST("/signup")
    @Headers({"Content-Type: application/json"})
    Call<ResponseBody> SignUp(@Body User myUser);

    @POST("/sign-in-with-google")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> signInWithGoogle(@Body GoogleSignInAccount account);

    @POST("/sign-in-with-facebook")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> signInWithFacebook(@Body Profile profile);

    @POST("/send-verification-email")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkEmail(@Body User user);

    @POST("/send-verification-code")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkPhone(@Body User user);

    @POST("/check-verification")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> checkVerification(@Body VerificationData verificationData);

    @POST("/forgot-password")
    @Headers({"Content-Type: application/json"})
    Call<ServerAnswer> forgotPassword(@Body AuthUserInfo user);

    @POST("/get-user")
    @Headers({"Content-Type: application/json"})
    Call<User> getUser(@Query("access_token") String token);

}
