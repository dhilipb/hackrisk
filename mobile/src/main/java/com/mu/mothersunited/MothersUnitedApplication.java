package com.mu.mothersunited;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;

public class MothersUnitedApplication extends Application
{
    private static final String KEY_FACEBOOK_ID = "KEY_FACEBOOK_ID";
    private static final String KEY_FACEBOOK_NAME = "KEY_FACEBOOK_NAME";
    private static final String KEY_FACEBOOK_AGE = "KEY_FACEBOOK_AGE";
    private static final String KEY_FACEBOOK_TOKEN = "KEY_FACEBOOK_TOKEN";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate()
    {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public String getFacebookId()
    {
        return sharedPreferences.getString(KEY_FACEBOOK_ID, null);
    }

    public void setFacebookId(String facebookId)
    {
        if (facebookId == null) {
            sharedPreferences.edit().remove(KEY_FACEBOOK_ID).apply();
        } else {
            sharedPreferences.edit().putString(KEY_FACEBOOK_ID, facebookId).apply();
        }
    }

    public String getFacebookAccessToken()
    {
        return sharedPreferences.getString(KEY_FACEBOOK_TOKEN, null);
    }

    public void setFacebookAccessToken(String facebookAccessToken)
    {
        if (facebookAccessToken == null) {
            sharedPreferences.edit().remove(KEY_FACEBOOK_TOKEN).apply();
        } else {
            sharedPreferences.edit().putString(KEY_FACEBOOK_TOKEN, facebookAccessToken).apply();
        }
    }

    public String getFacebookName()
    {
        return sharedPreferences.getString(KEY_FACEBOOK_NAME, null);
    }

    public void setFacebookName(String facebookName)
    {
        if (facebookName == null) {
            sharedPreferences.edit().remove(KEY_FACEBOOK_NAME).apply();
        } else {
            sharedPreferences.edit().putString(KEY_FACEBOOK_NAME, facebookName).apply();
        }
    }

    public int getFacebookAge()
    {
        return sharedPreferences.getInt(KEY_FACEBOOK_AGE, -1);
    }

    public void setFacebookAge(int facebookAge)
    {
        if (facebookAge == -1) {
            sharedPreferences.edit().remove(KEY_FACEBOOK_AGE).apply();
        } else {
            sharedPreferences.edit().putInt(KEY_FACEBOOK_AGE, facebookAge).apply();
        }
    }

}
