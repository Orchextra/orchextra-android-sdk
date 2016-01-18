package com.gigigo.orchextra.domain.interactors.geofences;

import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

public class RetrieveGeofenceDistanceInteractor implements Interactor<InteractorResponse<Double>> {

    private final GeofenceDataProvider geofenceDataProvider;
    private String geofenceId;
    private Point triggeringPoint;

    public RetrieveGeofenceDistanceInteractor(GeofenceDataProvider geofenceDataProvider) {
        this.geofenceDataProvider = geofenceDataProvider;
    }

    @Override
    public InteractorResponse<Double> call() throws Exception {
        Geofence geofence = geofenceDataProvider.obtainGeofenceByIdFromDatabase(geofenceId);
 /// Calcular distancia
        return null;
    }

    public void setGeofenceId(String geofenceId) {
        this.geofenceId = geofenceId;
    }

    public void setTriggeringPoint(Point triggeringPoint) {
        this.triggeringPoint = triggeringPoint;
    }
}
