package com.gigigo.orchextra.dataprovision.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

public class ProximityLocalDataProviderImp implements ProximityLocalDataProvider {

    private final ConfigDBDataSource configDBDataSource;
    private final BeaconsDBDataSource beaconsDBDataSource;

    public ProximityLocalDataProviderImp(ConfigDBDataSource configDBDataSource,
        BeaconsDBDataSource beaconsDBDataSource) {
        this.configDBDataSource = configDBDataSource;
        this.beaconsDBDataSource = beaconsDBDataSource;
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceByCodeFromDatabase(String geofenceId) {
        return configDBDataSource.obtainGeofenceById(geofenceId);
    }

    @Override public BusinessObject<List<OrchextraRegion>> getBeaconRegionsForScan() {
        return configDBDataSource.obtainRegionsForScan();
    }

    @Override public BusinessObject<OrchextraRegion> obtainRegion(OrchextraRegion orchextraRegion) {
        return beaconsDBDataSource.obtainRegionEvent(orchextraRegion);
    }

    @Override public BusinessObject<OrchextraRegion> storeRegion(OrchextraRegion orchextraRegion) {
        return beaconsDBDataSource.storeRegionEvent(orchextraRegion);
    }

    @Override public BusinessObject<OrchextraRegion> deleteRegion(OrchextraRegion orchextraRegion) {
        return beaconsDBDataSource.deleteRegionEvent(orchextraRegion);
    }

    @Override public BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon) {
        return beaconsDBDataSource.storeBeaconEvent(beacon);
    }

    @Override public boolean purgeOldBeaconEventsWithRequestTime(List<OrchextraBeacon> beacons,
        int requestTime) {
        return beaconsDBDataSource.deleteAllBeaconsInListWithTimeStampt(beacons, requestTime);
    }

    @Override public boolean isBeaconEventStored(OrchextraBeacon beacon) {
        return beaconsDBDataSource.isBeaconEventStored(beacon);
    }

    @Override public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
        return beaconsDBDataSource.updateRegionWithActionId(orchextraRegion);
    }



}
