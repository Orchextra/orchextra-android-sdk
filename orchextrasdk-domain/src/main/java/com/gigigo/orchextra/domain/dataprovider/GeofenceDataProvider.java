package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Geofence;

import java.util.List;

public interface GeofenceDataProvider {
    BusinessObject<List<Geofence>> obtainGeofencesFromDatabase();

    BusinessObject<Geofence> obtainGeofenceByIdFromDatabase(String geofenceId);
}
