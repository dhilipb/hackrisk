package com.mu.mothersunited.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mu.mothersunited.MainActivity;
import com.mu.mothersunited.MothersUnitedApplication;
import com.mu.mothersunited.MothersUnitedUtil;
import com.mu.mothersunited.R;
import com.mu.mothersunited.facebook.FacebookUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.login_button)
    LoginButton loginButton;

    private CallbackManager callbackManager;
    private MothersUnitedApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        app = (MothersUnitedApplication) getApplication();

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("user_friends,public_profile,user_birthday");
        loginButton.registerCallback(callbackManager, new FacebookLoginCallback());
    }

    public void onFacebookLoggedIn(FacebookUser user) {
        app.setUser(user);
        Intent intent = new Intent(this, WhoAreYouActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private class FacebookLoginCallback implements FacebookCallback<LoginResult> {

        @Override
        public void onSuccess(LoginResult loginResult) {
            final AccessToken accessToken = loginResult.getAccessToken();

            GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.v("LoginActivity", response.toString());

                    try {
                        String name = object.getString("name");
                        String facebookId = object.getString("id");
                        String birthdayStr = object.getString("birthday");

                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new SimpleDateFormat("dd/mm/yyyy").parse(birthdayStr));

                        int age = MothersUnitedUtil.getAge(cal.getTime());

                        FacebookUser user = new FacebookUser(facebookId, name, age);
                        LoginActivity.this.onFacebookLoggedIn(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {
            Toast.makeText(LoginActivity.this, "Could not login to Facebook, please try again", Toast.LENGTH_SHORT);
        }
    }

}
