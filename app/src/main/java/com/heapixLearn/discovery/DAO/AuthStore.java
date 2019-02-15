package com.heapixLearn.discovery.DAO;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.heapixLearn.discovery.App;
import com.heapixLearn.discovery.server.contacts.Person;

import static android.content.Context.MODE_PRIVATE;


public class AuthStore {

    private static final String SAVED_LOGIN = "saved_login";
    private static final String SAVED_TOKEN = "saved_token";
    private static final String SAVED_USER = "saved_user";
    private SharedPreferences preferences;

    public AuthStore(){
        preferences = App.getInstance().getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }


    public void saveLogin(String login) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_LOGIN, login);
        editor.apply();
    }

    public void saveUser(Person person) {
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(person);
        editor.putString(SAVED_USER, json);
        editor.apply();
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_TOKEN, token);
        editor.apply();
    }

    public Person getUser() {
        Gson gson = new Gson();
        String json = preferences.getString(SAVED_USER, "");
        return gson.fromJson(json, Person.class);
    }

    public String getToken() {
        return preferences.getString(SAVED_TOKEN, "");
    }


    public String getLogin() {
        return preferences.getString(SAVED_LOGIN, "");
    }

}
