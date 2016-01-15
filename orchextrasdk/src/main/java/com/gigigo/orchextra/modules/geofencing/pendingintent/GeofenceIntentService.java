package com.gigigo.orchextra.modules.geofencing.pendingintent;

import android.app.IntentService;
import android.content.Intent;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.delegates.ProximityItemDelegateImp;

public class GeofenceIntentService extends IntentService {

    public static final String TAG = GeofenceIntentService.class.getSimpleName();

    public GeofenceIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GGGLogImpl.log("Localizado");

        ProximityItemDelegateImp proximityItemDelegateImp = new ProximityItemDelegateImp();
        proximityItemDelegateImp.processGeofenceIntentPending(intent);
    }
}
