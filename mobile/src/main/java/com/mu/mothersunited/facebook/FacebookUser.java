package com.mu.mothersunited.facebook;

import android.content.SharedPreferences;

import com.mu.mothersunited.model.UserType;

import java.util.HashSet;
import java.util.Set;

public class FacebookUser {

    private static final String KEY_FACEBOOK_ID = "KEY_FACEBOOK_ID";
    private static final String KEY_FACEBOOK_NAME = "KEY_FACEBOOK_NAME";
    private static final String KEY_FACEBOOK_AGE = "KEY_FACEBOOK_AGE";
    private static final String KEY_FACEBOOK_TYPE = "KEY_FACEBOOK_TYPE";
    private static final String KEY_FACEBOOK_FRIENDS = "KEY_FACEBOOK_FRIENDS";
    private static final String KEY_PREGNANCY_MONTHS = "KEY_PREGNANCY_MONTHS";

    private String id;
    private String name;
    private int age;
    private int pregnancyMonths;
    private UserType userType;

    private Set<String> friends;

    public FacebookUser(String id, String name) {
        this.id = id;
        this.name = name;
        this.userType = UserType.EXPECTING;
        this.friends = new HashSet<>();
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
        String userType = sharedPreferences.getString(KEY_FACEBOOK_TYPE, null);
        Set<String> friends = sharedPreferences.getStringSet(KEY_FACEBOOK_FRIENDS, new HashSet<String>());

        if (id != null && name != null && age != -1) {
			FacebookUser user = new FacebookUser(id, name, age);
            user.setUserType(userType);
            user.setFriends(friends);
            user.setPregnancyMonths(sharedPreferences.getInt(KEY_PREGNANCY_MONTHS, 0));
            return user;
        }
        return null;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setUserType(String userType) {
        for (UserType type : UserType.values()) {
            if (type.getType().equals(userType)) {
                this.userType = type;
            }
        }
    }

    public void save(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().putString(KEY_FACEBOOK_ID, id).apply();
        sharedPreferences.edit().putString(KEY_FACEBOOK_NAME, name).apply();
        sharedPreferences.edit().putInt(KEY_FACEBOOK_AGE, age).apply();
        sharedPreferences.edit().putString(KEY_FACEBOOK_TYPE, userType.getType()).apply();
        sharedPreferences.edit().putStringSet(KEY_FACEBOOK_FRIENDS, friends).apply();
        sharedPreferences.edit().putInt(KEY_PREGNANCY_MONTHS, pregnancyMonths).apply();
    }

    public void clear(SharedPreferences sharedPreferences) {
        sharedPreferences.edit().remove(KEY_FACEBOOK_ID).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_NAME).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_AGE).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_TYPE).apply();
        sharedPreferences.edit().remove(KEY_FACEBOOK_FRIENDS).apply();
        sharedPreferences.edit().remove(KEY_PREGNANCY_MONTHS).apply();
    }

    public Set<String> getFriends() {
        Set<String> meAndFriends = friends;
        meAndFriends.add(id);
        return meAndFriends;
    }

    public void setFriends(Set<String> friends) {
        this.friends = friends;
    }
}
