package com.mu.mothersunited.model;

public class PushToken
{
    public String facebookId;
    public String deviceId;

    public PushToken(String facebookId, String deviceId)
    {
        this.facebookId = facebookId;
        this.deviceId = deviceId;
    }

}
