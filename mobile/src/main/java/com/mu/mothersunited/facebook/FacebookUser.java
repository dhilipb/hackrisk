package com.mu.mothersunited.facebook;

import android.content.SharedPreferences;
import com.mu.mothersunited.model.User;

import java.util.List;

public class FacebookUser {

    private static final String KEY_FACEBOOK_ID = "KEY_FACEBOOK_ID";
    private static final String KEY_FACEBOOK_NAME = "KEY_FACEBOOK_NAME";
    private static final String KEY_FACEBOOK_AGE = "KEY_FACEBOOK_AGE";
    private static final String KEY_PREGNANCY_MONTHS = "KEY_PREGNANCY_MONTHS";

    private String id;
    private String name;
    private int age;
    private int pregnancyMonths;

    private List<String> friends;

    public FacebookUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FacebookUser(String id, String name, int age) {
        this(id, name);
        this.age = age;
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

    public int getPregnancyMonths()
    {
        return pregnancyMonths;
    }

    public void setPregnancyMonths(int pregnancyMonths)
    {
        this.pregnancyMonths = pregnancyMonths;
    }

    public static FacebookUser restore(SharedPreferences sharedPreferences) {
        String id = sharedPreferences.getString(KEY_FACEBOOK_ID, null);
        String name = sharedPreferences.getString(KEY_FACEBOOK_NAME, null);
        int age = sharedPreferences.getInt(KEY_FACEBOOK_AGE, -1);

        if (id != null && name != null && age != -1) {
			FacebookUser user = new FacebookUser(id, name, age);
            user.setPregnancyMonths(sharedPreferences.getInt(KEY_PREGNANCY_MONTHS, 0));
            return user;
        }
        return null;
    }

    public void save(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putString(KEY_FACEBOOK_ID, id).apply();
        sharedPreferences.edit().putString(KEY_FACEBOOK_NAME, name).apply();
        sharedPreferences.edit().putInt(KEY_FACEBOOK_AGE, age).apply();
        sharedPreferences.edit().putInt(KEY_PREGNANCY_MONTHS, pregnancyMonths).apply();
    }

    public void clear(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().remove(KEY_FACEBOOK_ID).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_NAME).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_AGE).apply();
        sharedPreferences.edit().remove(KEY_PREGNANCY_MONTHS).apply();
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
