package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.List;

public class RetrieveGeofencesFromDatabaseInteractor implements Interactor<InteractorResponse<List<OrchextraGeofence>>> {

    private final GeofenceDataProvider geofenceDataProvider;

    public RetrieveGeofencesFromDatabaseInteractor(GeofenceDataProvider geofenceDataProvider) {
        this.geofenceDataProvider = geofenceDataProvider;
    }

    @Override
    public InteractorResponse<List<OrchextraGeofence>> call() throws Exception {
        BusinessObject<List<OrchextraGeofence>> boProximityItemList = geofenceDataProvider.obtainGeofencesFromDatabase();

        if (boProximityItemList.isSuccess()) {
            List<OrchextraGeofence> geofenceList = boProximityItemList.getData();

            return new InteractorResponse<>(geofenceList);
        } else {
            return new InteractorResponse<>(new RetrieveProximityItemError(boProximityItemList.getBusinessError()));
        }
    }
}
