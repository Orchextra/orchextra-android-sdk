package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

public interface ProximityLocalDataProvider {
    BusinessObject<OrchextraRegion> obtainRegion(OrchextraRegion orchextraRegion);
    BusinessObject<OrchextraRegion> storeRegion(OrchextraRegion orchextraRegion);
    BusinessObject<OrchextraRegion> deleteRegion(OrchextraRegion orchextraRegion);
    BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon);
    BusinessObject<List<OrchextraRegion>> getBeaconRegionsForScan();
    boolean purgeOldBeaconEventsWithRequestTime(List<OrchextraBeacon> beacons, int requestTime);
    boolean isBeaconEventStored(OrchextraBeacon beacon);
    BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion);

    BusinessObject<OrchextraGeofence> storeGeofenceEvent(String geofenceId);
    BusinessObject<OrchextraGeofence> deleteGeofenceEvent(String geofenceId);
    BusinessObject<OrchextraGeofence> obtainGeofenceEvent(String geofenceId);
}
