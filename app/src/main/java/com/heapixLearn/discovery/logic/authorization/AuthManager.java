package com.heapixLearn.discovery.logic.authorization;


import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.heapixLearn.discovery.User;
import com.heapixLearn.discovery.db.authorization.AuthStore;
import com.heapixLearn.discovery.server.Controller;
import com.heapixLearn.discovery.server.ServerAnswer;
import com.heapixLearn.discovery.server.UserApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthManager implements AuthManagerInterface {

    private AuthorizationErrors.TypeOfAuthManagerError error;
    private AuthStore store;
    private static AuthManager instance;
    private final UserApi userApi;
    private VerificationData verificationData;

    private AuthManager(){
        store = AuthStore.getInstance();
        userApi = Controller.getApi();
        verificationData =new VerificationData();
    }

    public static AuthManager getInstance(){
        if (instance==null) instance = new AuthManager();
        return instance;
    }

    @Override
    public void tryLoginWith(final String login, final String password, final Runnable onSuccess, final RunnableWithError onFailure) {
        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };

        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setLogin(login);
        authUserInfo.setPassword(password);

        userApi.checkLogin(authUserInfo).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, final Response<ServerAnswer> response) {

                if (!isExist(response.body())) {
                    Gson gson = new Gson();
                    ServerAnswer serverAnswer=gson.fromJson(response.errorBody().charStream(),ServerAnswer.class);
                    error = AuthorizationErrors.convertError(serverAnswer.getError());
                    onFailureUserCheck.run();
                    return;
                }
                if (response.body().getSuccess() && response.body().getToken()!=null){
                    store.saveToken(response.body().getToken());
                    store.saveLogin(login);
                    saveUser();
                    onSuccess.run();
                }
                else {
                    error = AuthorizationErrors.TypeOfAuthManagerError.USER_CHECK_ERROR;
                    onFailureUserCheck.run();
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                error = AuthorizationErrors.TypeOfAuthManagerError.SERVER_ERROR;
                onFailureUserCheck.run();
            }
        });
    }

    @Override
    public void tryLoginWithGoogle(final GoogleSignInAccount account, final Runnable onSuccess, final RunnableWithError onFailure) {
        this.error = null;
        userApi.signInWithGoogle(account).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        store.saveLogin(account.getEmail());
                        store.saveToken(response.body().getToken());
                        onSuccess.run();
                    }else onFailure.run();
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void tryLoginWithFacebook(final Profile account, final Runnable onSuccess, final RunnableWithError onFailure) {
        this.error = null;
        userApi.signInWithFacebook(account).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        store.saveLogin(account.getName());
                        store.saveToken(response.body().getToken());
                        saveUser();
                        onSuccess.run();
                    }
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void tryLoginWithStoredInfo(final Runnable onSuccess, final Runnable onFailure) {
        tryLoginWithToken(store.getToken(), onSuccess, onFailure);
    }

    private void tryLoginWithToken(String token, final Runnable onSuccess, final Runnable onFailure){
        userApi.getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (isExist(response.body())){
                    onSuccess.run();
                }
                else{
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void tryRegistrationWith(User myUser, final Runnable onSuccess, final RunnableWithError onFailure) {
        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };

        userApi.SignUp(myUser).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.errorBody()!=null) {
                    Gson gson = new Gson();
                    ServerAnswer serverAnswer=gson.fromJson(response.errorBody().charStream(),ServerAnswer.class);
                    error = AuthorizationErrors.convertError(serverAnswer.getError());
                    onFailureUserCheck.run();
                }
                else{
                    onSuccess.run();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error = AuthorizationErrors.TypeOfAuthManagerError.SERVER_ERROR;
                onFailureUserCheck.run();
            }
        });
    }

    @Override
    public void sendEmail(String email, final Runnable onSuccess, final RunnableWithError onFailure) {
        if (!checkEmail(email)) {
            onFailure.init(AuthorizationErrors.TypeOfAuthManagerError.CHECK_EMAIL);
            onFailure.run();
        }

        User user = new User();
        user.setEmail(email);
        userApi.checkEmail(user).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        verificationData.setToken(response.body().getToken());
                        onSuccess.run();
                    }
                    else onFailure.run();
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });

    }

    @Override
    public void checkEmailVerification(final Runnable onSuccess, final RunnableWithError onFailure) {
        userApi.checkVerification(verificationData).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    }
                    else onFailure.run();
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    private boolean checkEmail(String mEmail){
        int posDog = mEmail.indexOf("@");
        int posDot = mEmail.indexOf(".");
        return (posDog != -1) && (Math.abs(posDog - posDot) > 1) && (posDot != -1);
    }


    @Override
    public void sendSMSToPhone(String phone, final Runnable onSuccess, final RunnableWithError onFailure) {
        User user = new User();
        user.setPhone(phone);
        userApi.checkPhone(user).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        verificationData.setToken(response.body().getToken());
                        onSuccess.run();
                    }else onFailure.run();
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void checkPhoneVerification(String code, final Runnable onSuccess, final RunnableWithError onFailure) {
        verificationData.setCode(code);
        userApi.checkVerification(verificationData).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (isExist(response.body())){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    }
                    else {
                        onFailure.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void forgotPassword(String login, final Runnable onSuccess, final RunnableWithError onFailure) {
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setLogin(login);
        userApi.forgotPassword(authUserInfo).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    }
                    else onFailure.run();
                }
                else onFailure.run();
            }

            @Override
            public void onFailure(Call<ServerAnswer> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public String getStoreLogin() {
        return store.getLogin();
    }

    private void saveUser(){
        userApi.getUser(store.getToken()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (isExist(response.body())){
                    store.saveUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private boolean isExist(User user){
        return user!=null;
    }

    private boolean isExist(ServerAnswer serverAnswer){
        return serverAnswer!=null;
    }

}