package com.mu.mothersunited.facebook;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dhilipb on 30/05/2015.
 */
public class MUCallback implements FacebookCallback<LoginResult> {

    MUFacebookListener facebookListener;

    public void setListener(MUFacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        final AccessToken accessToken = loginResult.getAccessToken();

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.v("LoginActivity", response.toString());

                try {
                    String name = object.getString("name");
//                    String email = object.getString("email");
//                    String gender = object.getString("gender");
                    String authToken = accessToken.getToken();
                    String facebookId = object.getString("id");
//                    Date expires = accessToken.getExpires();
                    JSONObject ageRange = object.getJSONObject("age_range");
                    String age = ageRange.getString("min") + "-" + ageRange.getString("max");

                    facebookListener.onFacebookLoggedIn(facebookId, authToken, name, age);

                } catch (JSONException e) {

                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException exception) {

    }
}
