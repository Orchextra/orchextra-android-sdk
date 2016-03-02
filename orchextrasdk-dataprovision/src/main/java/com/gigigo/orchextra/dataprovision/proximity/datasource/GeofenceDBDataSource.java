package com.gigigo.orchextra.dataprovision.proximity.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

public interface GeofenceDBDataSource {

    BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence data);

    BusinessObject<OrchextraGeofence> deleteGeofenceEvent(OrchextraGeofence data);

    BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence data);

    BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence);
}
