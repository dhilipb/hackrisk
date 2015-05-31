package com.mu.mothersunited.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.mu.mothersunited.MainActivity;
import com.mu.mothersunited.MothersUnitedApplication;
import com.mu.mothersunited.R;
import com.mu.mothersunited.adapter.FacebookFriendsAdapter;
import com.mu.mothersunited.facebook.FacebookUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectFriendsActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private MothersUnitedApplication app;

    @InjectView(R.id.lstSelectFriends)
    RecyclerView mListSelectFriends;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    FacebookFriendsAdapter friendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MothersUnitedApplication) getApplication();
        callbackManager = CallbackManager.Factory.create();
        retrieveFriendsList();

        setContentView(R.layout.activity_select_friends);
        ButterKnife.inject(this);

        toolbar.setTitle("Add to your circle");
        setSupportActionBar(toolbar);

        friendsAdapter = new FacebookFriendsAdapter();
        mListSelectFriends.setAdapter(friendsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_next:

                Set<String> friends = new HashSet<String>();
                for (FacebookUser user : friendsAdapter.getFriends()) {
                    friends.add(user.getId());
                }

                FacebookUser facebookUser = app.getUser();
                facebookUser.setFriends(friends);
                facebookUser.save(PreferenceManager.getDefaultSharedPreferences(this));

                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void retrieveFriendsList() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest.newMyFriendsRequest(accessToken, new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray jsonArray, GraphResponse response) {
                Log.v("LoginActivity", response.toString());

                if (jsonArray.length() == 0) {
                    return;
                }

                List<FacebookUser> friends = new ArrayList<FacebookUser>();
                try {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.getString("id");
                        String name = obj.getString("name");

                        FacebookUser friend = new FacebookUser(id, name);
                        friends.add(friend);
                    }

                    friendsAdapter.setFriends(friends);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).executeAsync();
    }
}
