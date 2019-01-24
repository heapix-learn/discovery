package com.heapixLearn.discovery.logic.authorization;

import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.heapixLearn.discovery.User;

public interface AuthManagerInterface {
    void tryLoginWith(String login, String password, Runnable onSuccess, RunnableWithError onFailure);
    void tryLoginWithGoogle(GoogleSignInAccount account, Runnable onSuccess, RunnableWithError onFailure);
    void tryLoginWithFacebook(Profile profile, Runnable onSuccess, RunnableWithError onFailure);
    void tryLoginWithStoredInfo(Runnable onSuccess, Runnable onFailure);
    void tryRegistrationWith(User user, Runnable onSuccess, RunnableWithError onFailure);
    void sendEmail(String email, Runnable onSuccess, RunnableWithError onFailure);
    void checkEmailVerification(Runnable onSuccess, RunnableWithError onFailure);
    void sendSMSToPhone(String phone, Runnable onSuccess, RunnableWithError onFailure);
    void checkPhoneVerification(String code, Runnable onSuccess, RunnableWithError onFailure);
    void forgotPassword(String login, Runnable onSuccess, RunnableWithError onFailure);
    String getStoreLogin();
}
