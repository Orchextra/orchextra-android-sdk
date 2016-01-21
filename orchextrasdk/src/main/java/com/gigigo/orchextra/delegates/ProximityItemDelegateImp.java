package com.gigigo.orchextra.delegates;

import android.content.Intent;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.android.proximity.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemDelegate;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import javax.inject.Inject;

public class ProximityItemDelegateImp implements ProximityItemDelegate {

    @Inject
    ProximityItemController controller;

    @Inject
    AndroidGeofenceManager androidGeofenceManager;

    private DelegateComponent delegateComponent;

    public ProximityItemDelegateImp() {
        init();
    }

    @Override
    public void init() {
        delegateComponent = Orchextra.getInjector().injectProximityItemDelegate(this);
        delegateComponent.injectProximityItemDelegate(this);
        controller.attachDelegate(this);
    }

    @Override
    public void destroy() {
        controller.detachDelegate();
        delegateComponent = null;
    }

    @Override
    public void onControllerReady() {
        controller.retrieveGeofences();
    }

    @Override
    public void registerGeofences(List<ControlGeofence> geofenceList) {
        androidGeofenceManager.registerGeofences(geofenceList);
    }

    public void processGeofenceIntentPending(Intent intent) {
        GeofencingEvent geofencingEvent = androidGeofenceManager.getGeofencingEvent(intent);

        List<String> geofenceIds = androidGeofenceManager.getTriggeringGeofenceIds(geofencingEvent);
        ControlPoint point = androidGeofenceManager.getTriggeringPoint(geofencingEvent);
        GeoPointEventType transition = androidGeofenceManager.getGeofenceTransition(geofencingEvent);

        controller.processTriggers(geofenceIds, point, transition);
    }
}
