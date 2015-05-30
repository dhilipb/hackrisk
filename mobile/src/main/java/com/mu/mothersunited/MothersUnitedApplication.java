package com.mu.mothersunited;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.FacebookSdk;
import com.mu.mothersunited.facebook.FacebookUser;

public class MothersUnitedApplication extends Application
{

    private SharedPreferences sharedPreferences;
    private MothersUnitedApi api;

    private FacebookUser facebookUser;

    @Override
    public void onCreate()
    {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MothersUnitedApi.BASE_URL)
                .build();
        api = restAdapter.create(MothersUnitedApi.class);
    }

    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setFacebookUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }

}
