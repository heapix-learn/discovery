package com.heapixLearn.discovery.server.authorization;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.heapixLearn.discovery.entity.authorization.Email;
import com.heapixLearn.discovery.entity.authorization.Person;
import com.heapixLearn.discovery.entity.authorization.Phone;
import com.heapixLearn.discovery.logic.authorization.AuthInfo;
import com.heapixLearn.discovery.logic.authorization.VerificationCode;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface AuthApi {

    @POST("/signin")
    Call<ServerAnswer> checkLogin(@Body AuthInfo info);

    @POST("/signup")
    Call<ResponseBody> SignUp(@Body Person myPerson);

    @POST("/sign-in-with-google")
    Call<ServerAnswer> signInWithGoogle(@Body GoogleSignInAccount account);

    @POST("/sign-in-with-facebook")
    Call<ServerAnswer> signInWithFacebook(@Body Profile profile);

    @POST("/verification")
    Call<ServerAnswer> checkEmail(@Body Email email);

    @POST("/verification")
    Call<ServerAnswer> checkPhone(@Body Phone phone);

    @POST("/verification")
    Call<ServerAnswer> checkVerification(@Body VerificationCode verificationCode, @Header("Authorization") String token);

    @POST("/verification")
    Call<ServerAnswer> checkVerification(@Header("Authorization") String token);

    @POST("/forgot-password")
    Call<ServerAnswer> forgotPassword(@Body AuthInfo user);

    @GET("/user")
    Call<Person> getPerson(@Header("Authorization") String token);

}
