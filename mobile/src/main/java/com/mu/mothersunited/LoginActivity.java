package com.mu.mothersunited;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.mu.mothersunited.facebook.FacebookUser;
import com.mu.mothersunited.facebook.MUCallback;
import com.mu.mothersunited.facebook.MUFacebookListener;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends AppCompatActivity implements MUFacebookListener {

    @InjectView(R.id.login_button)
    LoginButton loginButton;

    private CallbackManager callbackManager;
    private MothersUnitedApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        app = (MothersUnitedApplication) getApplication();
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("user_friends,public_profile,user_birthday");

        MUCallback callback = new MUCallback();
        callback.setListener(this);
        loginButton.registerCallback(callbackManager, callback);

        if (app.getFacebookUser() == null) {
            LoginManager.getInstance().registerCallback(callbackManager, callback);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void onFacebookLoggedIn(FacebookUser user) {
        app.setFacebookUser(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
