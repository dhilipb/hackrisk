package com.mu.mothersunited;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.mu.mothersunited.facebook.FacebookUser;
import com.mu.mothersunited.model.Comment;
import com.mu.mothersunited.model.PushToken;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.IOException;
import java.util.List;

public class MothersUnitedApplication extends Application
{
    private MothersUnitedApi api;
    private FacebookUser facebookUser;
    private SharedPreferences sharedPreferences;
    private String pushToken;

    @Override
    public void onCreate()
    {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        facebookUser = FacebookUser.restore(sharedPreferences);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(MothersUnitedApi.BASE_URL)
                .build();
        api = restAdapter.create(MothersUnitedApi.class);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params)
            {
                InstanceID instanceID = InstanceID.getInstance(MothersUnitedApplication.this);
                String token = null;
                try
                {
                    token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Log.i("MothersUnited", "GCM Registration Token: " + token);
                return token;
            }

            @Override
            protected void onPostExecute(String token)
            {
                super.onPostExecute(token);
                if (token == null) {
                    return;
                }

                MothersUnitedApplication.this.pushToken = token;
                if (getUser() != null) {
                    api.addPushToken(new PushToken(getUser().getId(), pushToken), new Callback<List<Comment>>() {
                        @Override
                        public void success(List<Comment> comments, Response response)
                        {

                        }

                        @Override
                        public void failure(RetrofitError error)
                        {

                        }
                    });
                }
            }
        }.execute();
    }

    public FacebookUser getUser() {
        return facebookUser;
    }

    public void setUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
        this.facebookUser.save(sharedPreferences);
        if (pushToken != null) {
            api.addPushToken(new PushToken(getUser().getId(), pushToken), new Callback<List<Comment>>() {
                @Override
                public void success(List<Comment> comments, Response response)
                {

                }

                @Override
                public void failure(RetrofitError error)
                {

                }
            });
        }
    }

    public MothersUnitedApi getApi()
    {
        return api;
    }

}
