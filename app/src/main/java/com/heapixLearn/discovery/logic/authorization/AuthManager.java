package com.heapixLearn.discovery.logic.authorization;


import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.heapixLearn.discovery.entity.authorization.Email;
import com.heapixLearn.discovery.entity.authorization.Person;
import com.heapixLearn.discovery.db.authorization.AuthStore;
import com.heapixLearn.discovery.entity.authorization.Phone;
import com.heapixLearn.discovery.server.authorization.Controller;
import com.heapixLearn.discovery.server.authorization.ServerAnswer;
import com.heapixLearn.discovery.server.authorization.AuthApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthManager implements AuthManagerWith {

    private AuthorizationErrors.TypeOfAuthManagerError error;
    private AuthStore store;
    private AuthApi authApi;
    private String verificationToken;

    public AuthManager(){
        store = new AuthStore();
        authApi = Controller.getApi();
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

        AuthInfo authInfo = new AuthInfo();
        authInfo.setLogin(login);
        authInfo.setPassword(password);

        authApi.checkLogin(authInfo).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, final Response<ServerAnswer> response) {

                if (response.errorBody()!=null) {
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
        authApi.signInWithGoogle(account).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
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
        authApi.signInWithFacebook(account).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
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
        authApi.getPerson(token).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                    onSuccess.run();
                }
                else{
                    onFailure.run();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                onFailure.run();
            }
        });
    }

    @Override
    public void tryRegistrationWith(Person myPerson, final Runnable onSuccess, final RunnableWithError onFailure) {
        final Runnable onFailureUserCheck = new Runnable() {
            @Override
            public void run() {
                onFailure.init(error);
                onFailure.run();
            }
        };

        authApi.SignUp(myPerson).enqueue(new Callback<ResponseBody>() {
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

        Email emailObject = new Email();
        emailObject.setEmail(email);
        authApi.checkEmail(emailObject).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        verificationToken = response.body().getToken();
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
        authApi.checkVerification(verificationToken).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess()){
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } else {
                    onFailure.run();
                }
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
        Phone phoneObject = new Phone();
        phoneObject.setPhone(phone);
        authApi.checkPhone(phoneObject).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
                    if (response.body().getSuccess() && (response.body().getToken()!=null)){
                        verificationToken = response.body().getToken();
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
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        authApi.checkVerification(verificationCode, verificationToken).enqueue(new Callback<ServerAnswer>() {
            @Override
            public void onResponse(Call<ServerAnswer> call, Response<ServerAnswer> response) {
                if (response.body()!=null){
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
        AuthInfo authInfo = new AuthInfo();
        authInfo.setLogin(login);
        authApi.forgotPassword(authInfo).enqueue(new Callback<ServerAnswer>() {
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
        authApi.getPerson(store.getToken()).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.body()!=null){
                    store.savePerson(response.body());
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });
    }


}