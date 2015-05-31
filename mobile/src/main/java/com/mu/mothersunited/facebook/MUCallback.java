package com.mu.mothersunited.facebook;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.mu.mothersunited.MothersUnitedUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dhilipb on 30/05/2015.
 */
public class MUCallback implements FacebookCallback<LoginResult> {

    MUFacebookListener facebookListener;
    static LoginResult loginResult;

    public void setListener(MUFacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        this.loginResult = loginResult;

        final AccessToken accessToken = loginResult.getAccessToken();

        GraphRequestBatch batch = new GraphRequestBatch(
                GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.v("LoginActivity", response.toString());

                try {
                    String name = object.getString("name");
                    String accessTokenStr = accessToken.getToken();
                    String facebookId = object.getString("id");
                    String birthdayStr = object.getString("birthday");

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("dd/mm/yyyy").parse(birthdayStr));

                    int age = MothersUnitedUtil.getAge(cal.getTime());

                    FacebookUser user = new FacebookUser(facebookId, name, age);
                    facebookListener.onFacebookLoggedIn(user);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }), GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray jsonArray, GraphResponse response) {
                Log.v("LoginActivity", response.toString());

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Log.v("obj", obj.toString());
                    }
                } catch (JSONException e) {

                }

            }
        }));

        batch.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException exception) {

    }



}
