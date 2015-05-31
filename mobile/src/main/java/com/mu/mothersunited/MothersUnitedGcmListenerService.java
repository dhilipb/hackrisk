package com.mu.mothersunited;

import android.os.Bundle;
import com.google.android.gms.gcm.GcmListenerService;

public class MothersUnitedGcmListenerService extends GcmListenerService
{
    @Override
    public void onMessageReceived(String from, Bundle data)
    {
        super.onMessageReceived(from, data);

    }
}
