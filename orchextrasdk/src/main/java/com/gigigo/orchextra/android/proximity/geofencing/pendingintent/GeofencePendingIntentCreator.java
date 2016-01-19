package com.gigigo.orchextra.android.proximity.geofencing.pendingintent;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class GeofencePendingIntentCreator {

    private final Context context;

    public GeofencePendingIntentCreator(Context context) {
        this.context = context;
    }

    public PendingIntent getGeofencingPendingIntent() {
        try {
            Intent intent = new Intent(context, GeofenceIntentService.class);
            return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } catch (Exception e) {
            return null;
        }
    }
}
