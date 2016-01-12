package com.gigigo.orchextra.delegates.proximity;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemDelegate;
import com.gigigo.orchextra.control.entities.GeofenceControl;
import com.gigigo.orchextra.delegates.proximity.manager.GeofenceRegister;
import com.gigigo.orchextra.di.components.DelegateComponent;

import java.util.List;

import javax.inject.Inject;

public class ProximityItemDelegateImp implements ProximityItemDelegate {

    @Inject
    ProximityItemController controller;

    GeofenceRegister geofenceRegister;

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
    }

    @Override
    public void registerGeofences(List<GeofenceControl> geofenceControlList) {
        geofenceRegister.register(geofenceControlList);
    }
}
