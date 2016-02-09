package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import java.util.List;

public interface ProximityLocalDataProvider {
    BusinessObject<OrchextraBeacon> obtainConcreteBeacon(OrchextraBeacon orchextraBeacon);
    BusinessObject<OrchextraGeofence> obtainGeofenceByCodeFromDatabase(String code);
}
