package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;

import java.util.List;

public interface GeofenceDataProvider {
    BusinessObject<List<OrchextraGeofence>> obtainGeofencesFromDatabase();

    BusinessObject<OrchextraGeofence> obtainGeofenceByIdFromDatabase(String geofenceId);
}
