package com.gigigo.orchextra.device.geolocation.geofencing.pendingintent;

import android.app.IntentService;
import android.content.Intent;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.google.android.gms.location.GeofencingEvent;
import java.util.List;
import javax.inject.Inject;

public class GeofenceIntentService extends IntentService {

    public static final String TAG = GeofenceIntentService.class.getSimpleName();

    @Inject
    AndroidGeofenceManager androidGeofenceManager;

    @Inject
    ProximityItemController controller;

    public GeofenceIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Orchextra.getInjector().injectGeofenceIntentServiceComponent(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GGGLogImpl.log("Localizado");
        processGeofenceIntentPending(intent);
    }

    public void processGeofenceIntentPending(Intent intent) {
        GeofencingEvent geofencingEvent = androidGeofenceManager.getGeofencingEvent(intent);

        List<String> geofenceIds = androidGeofenceManager.getTriggeringGeofenceIds(geofencingEvent);
        OrchextraPoint point = androidGeofenceManager.getTriggeringPoint(geofencingEvent);
        GeoPointEventType transition = androidGeofenceManager.getGeofenceTransition(geofencingEvent);

        controller.processTriggers(geofenceIds, point, transition);
    }

}
