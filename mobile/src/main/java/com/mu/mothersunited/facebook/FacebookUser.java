package com.mu.mothersunited.facebook;

import android.content.SharedPreferences;

/**
 * Created by dhilipb on 30/05/2015.
 */
public class FacebookUser {

    private static final String KEY_FACEBOOK_ID = "KEY_FACEBOOK_ID";
    private static final String KEY_FACEBOOK_NAME = "KEY_FACEBOOK_NAME";
    private static final String KEY_FACEBOOK_AGE = "KEY_FACEBOOK_AGE";
    private static final String KEY_FACEBOOK_TOKEN = "KEY_FACEBOOK_TOKEN";

    private String id;
    private String name;
    private int age;
    private String accessToken;

    public FacebookUser(String id, String name, int age, String accessToken) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static FacebookUser restore(SharedPreferences sharedPreferences) {
        String id = sharedPreferences.getString(KEY_FACEBOOK_ID, null);
        String accessToken = sharedPreferences.getString(KEY_FACEBOOK_TOKEN, null);
        String name = sharedPreferences.getString(KEY_FACEBOOK_NAME, null);
        int age = sharedPreferences.getInt(KEY_FACEBOOK_AGE, -1);

        if (id != null && accessToken != null && name != null && age != -1) {
            return new FacebookUser(id, name, age, accessToken);
        }
        return null;
    }

    public void save(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putString(KEY_FACEBOOK_ID, id).apply();
        sharedPreferences.edit().putString(KEY_FACEBOOK_TOKEN, accessToken).apply();
        sharedPreferences.edit().putString(KEY_FACEBOOK_NAME, name).apply();
        sharedPreferences.edit().putInt(KEY_FACEBOOK_AGE, age).apply();
    }

    public void clear(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().remove(KEY_FACEBOOK_ID).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_TOKEN).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_NAME).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_AGE).apply();
    }
}
