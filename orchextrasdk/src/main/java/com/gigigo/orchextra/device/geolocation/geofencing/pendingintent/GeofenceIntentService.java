package com.gigigo.orchextra.device.geolocation.geofencing.pendingintent;

import android.app.IntentService;
import android.content.Intent;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceIntentServiceHandler;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceEventException;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import javax.inject.Inject;

public class GeofenceIntentService extends IntentService {

    public static final String TAG = GeofenceIntentService.class.getSimpleName();

    @Inject
    AndroidGeofenceIntentServiceHandler geofenceHandler;

    @Inject
    GeofenceController controller;

    public GeofenceIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OrchextraManager.getInjector().injectGeofenceIntentServiceComponent(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        processGeofenceIntentPending(intent);
    }

    public void processGeofenceIntentPending(Intent intent) {
        GeofencingEvent geofencingEvent = geofenceHandler.getGeofencingEvent(intent);

        List<String> geofenceIds = geofenceHandler.getTriggeringGeofenceIds(geofencingEvent);

        try {
            GeoPointEventType transition = geofenceHandler.getGeofenceTransition(geofencingEvent);

            GGGLogImpl.log("Localizado: " + transition.getStringValue());

            controller.processTriggers(geofenceIds, transition);
        }catch (GeofenceEventException geofenceEventException){
            GGGLogImpl.log(geofenceEventException.getMessage(), LogLevel.ERROR);
        }
    }

}
