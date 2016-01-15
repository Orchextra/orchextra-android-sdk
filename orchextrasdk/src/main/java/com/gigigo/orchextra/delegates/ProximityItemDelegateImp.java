package com.gigigo.orchextra.delegates;

import android.content.Intent;
import android.location.Location;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemDelegate;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.entities.triggers.PhoneStatusType;
import com.gigigo.orchextra.modules.geofencing.GeofenceRegister;
import com.gigigo.orchextra.modules.geofencing.pendingintent.GeofenceIntentPendingManager;
import com.gigigo.orchextra.utils.location.LocationManager;
import com.gigigo.orchextra.utils.mapper.LocationMapper;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import javax.inject.Inject;

public class ProximityItemDelegateImp implements ProximityItemDelegate {

    @Inject
    ProximityItemController controller;

    @Inject
    GeofenceRegister geofenceRegister;

    @Inject
    GeofenceIntentPendingManager manager;

    @Inject
    LocationManager locationManager;

    @Inject
    LocationMapper locationMapper;

    private DelegateComponent delegateComponent;

    public ProximityItemDelegateImp() {
        init();
    }

    @Override
    public void init() {
        delegateComponent = Orchextra.getInjector().injectProximityItemDelegate(this);
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
        locationManager.initClient();
    }

    @Override
    public void registerGeofences(List<Geofence> geofenceList) {
        geofenceRegister.registerGeofences(geofenceList);
    }

    public void processGeofenceIntentPending(Intent intent) {
        GeofencingEvent geofencingEvent = manager.getGeofencingEvent(intent);

        if (!geofencingEvent.hasError()) {
            GeoPointEventType geofencingTransition = manager.getGeofenceTransition(geofencingEvent);
            Location triggeringLocation = manager.getTriggeringLocation(geofencingEvent);
            List<String> triggerGeofenceIds = manager.getTriggeringGeofenceIds(geofencingEvent);

            float distance = locationManager.calculateDistance(triggeringLocation);
            Point triggeringPoint = locationMapper.delegateToModel(triggeringLocation);
            PhoneStatusType phoneStatusType = Orchextra.getOchextraLifeCycle().getPhoneStatusType();

            controller.processTriggers(triggerGeofenceIds, triggeringPoint, phoneStatusType, distance, geofencingTransition);
        }
    }
}
