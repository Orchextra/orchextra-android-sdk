package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.GeofenceTrigger;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.ArrayList;
import java.util.List;

public class RetrieveGeofenceTriggerInteractor implements Interactor<InteractorResponse<List<Trigger>>> {

    private final ProximityLocalDataProvider proximityLocalDataProvider;
    private final AppRunningMode appRunningMode;
    private List<String> triggeringGeofenceIds;
    private OrchextraPoint triggeringPoint;
    private GeoPointEventType geofenceTransition;

    public RetrieveGeofenceTriggerInteractor(ProximityLocalDataProvider proximityLocalDataProvider, AppRunningMode appRunningMode) {
        this.proximityLocalDataProvider = proximityLocalDataProvider;
        this.appRunningMode = appRunningMode;
    }

    @Override
    public InteractorResponse<List<Trigger>> call() throws Exception {
        List<Trigger> triggers = new ArrayList<>();

        for (String triggeringGeofenceId : triggeringGeofenceIds) {
            BusinessObject<OrchextraGeofence> boGeofence = proximityLocalDataProvider.obtainGeofenceByCodeFromDatabase(
                triggeringGeofenceId);
            if (boGeofence.isSuccess()) {
                OrchextraGeofence geofence = boGeofence.getData();

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
    public void setTriggeringPoint(OrchextraPoint triggeringPoint) {
        this.triggeringPoint = triggeringPoint;
    }

    public void setTriggeringGeofenceIds(List<String> triggeringGeofenceIds) {
        this.triggeringGeofenceIds = triggeringGeofenceIds;
    }

    public void setGeofenceTransition(GeoPointEventType geofenceTransition) {
        this.geofenceTransition = geofenceTransition;
    }
}
