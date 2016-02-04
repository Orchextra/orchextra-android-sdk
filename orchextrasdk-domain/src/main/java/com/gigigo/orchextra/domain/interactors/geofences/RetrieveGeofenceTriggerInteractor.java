package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.entities.triggers.GeofenceTrigger;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.ArrayList;
import java.util.List;

public class RetrieveGeofenceTriggerInteractor implements Interactor<InteractorResponse<List<Trigger>>> {

    private final GeofenceDataProvider geofenceDataProvider;
    private final AppRunningMode appRunningMode;
    private List<String> triggeringGeofenceIds;
    private Point triggeringPoint;
    private GeoPointEventType geofenceTransition;

    public RetrieveGeofenceTriggerInteractor(GeofenceDataProvider geofenceDataProvider, AppRunningMode appRunningMode) {
        this.geofenceDataProvider = geofenceDataProvider;
        this.appRunningMode = appRunningMode;
    }

    @Override
    public InteractorResponse<List<Trigger>> call() throws Exception {
        List<Trigger> triggers = new ArrayList<>();

        for (String triggeringGeofenceId : triggeringGeofenceIds) {
            BusinessObject<Geofence> boGeofence = geofenceDataProvider.obtainGeofenceByIdFromDatabase(triggeringGeofenceId);
            if (boGeofence.isSuccess()) {
                Geofence geofence = boGeofence.getData();

                double distanceFromGeofenceInKm = triggeringPoint.getDistanceFromPointInKm(
                    geofence.getPoint());

                AppRunningModeType appRunningModeType = appRunningMode.getRunningModeType();

                GeofenceTrigger geofenceTrigger = Trigger.createGeofenceTrigger(triggeringGeofenceId, geofence.getPoint(),
                        appRunningModeType, distanceFromGeofenceInKm, geofenceTransition);

                triggers.add(geofenceTrigger);
            } else {
                return new InteractorResponse<>(new RetrieveProximityItemError(boGeofence.getBusinessError()));
            }
        }

        return new InteractorResponse<>(triggers);
    }
    public void setTriggeringPoint(Point triggeringPoint) {
        this.triggeringPoint = triggeringPoint;
    }

    public void setTriggeringGeofenceIds(List<String> triggeringGeofenceIds) {
        this.triggeringGeofenceIds = triggeringGeofenceIds;
    }

    public void setGeofenceTransition(GeoPointEventType geofenceTransition) {
        this.geofenceTransition = geofenceTransition;
    }
}
