package com.mu.mothersunited.facebook;

/**
 * Created by dhilipb on 30/05/2015.
 */
public interface MUFacebookListener {

    public void onFacebookLoggedIn(String facebookId, String authToken, String name, int age);


}
