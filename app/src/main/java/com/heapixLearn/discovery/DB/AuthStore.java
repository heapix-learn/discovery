package com.heapixLearn.discovery.DB;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.heapixLearn.discovery.AppContext;
import com.heapixLearn.discovery.User;

import static android.content.Context.MODE_PRIVATE;


public class AuthStore {

    private static final String SAVED_LOGIN = "saved_login";
    private static final String SAVED_TOKEN = "saved_token";
    private static final String SAVED_USER = "saved_user";
    private SharedPreferences preferences;
    private static AuthStore instance;

    private AuthStore(){
        preferences = AppContext.getInstance().getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }
    public static AuthStore getInstance(){
        if (instance ==null){
            instance = new AuthStore();
        }
        return instance;
    }

    public void saveLogin(String login) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_LOGIN, login);
        editor.apply();
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(SAVED_USER, json);
        editor.apply();
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TOKEN, token);
        editor.apply();
    }

    public User getUser() {
        Gson gson = new Gson();
        String json = preferences.getString(SAVED_USER, "");
        return gson.fromJson(json, User.class);
    }

    public String getToken() {
        return preferences.getString(SAVED_TOKEN, "");
    }


    public String getLogin() {
        return preferences.getString(SAVED_LOGIN, "");
    }

}
