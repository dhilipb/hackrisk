package com.mu.mothersunited;

import android.app.Application;
import com.facebook.FacebookSdk;

public class MothersUnitedApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}
