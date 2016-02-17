package com.gigigo.orchextra.dataprovision.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.datasource.GeofenceDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

public class ProximityLocalDataProviderImp implements ProximityLocalDataProvider {

    private final ConfigDBDataSource configDBDataSource;
    private final BeaconsDBDataSource beaconsDBDataSource;
    private final GeofenceDBDataSource geofenceDBDataSource;

    public ProximityLocalDataProviderImp(ConfigDBDataSource configDBDataSource,
                                         BeaconsDBDataSource beaconsDBDataSource,
                                         GeofenceDBDataSource geofenceDBDataSource) {
        this.configDBDataSource = configDBDataSource;
        this.beaconsDBDataSource = beaconsDBDataSource;
        this.geofenceDBDataSource = geofenceDBDataSource;
    }

    private BusinessObject<OrchextraGeofence> obtainSavedGeofenceInDatabase(String geofenceId) {
        return configDBDataSource.obtainGeofenceById(geofenceId);
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(String geofenceId) {
        BusinessObject<OrchextraGeofence> bo = obtainSavedGeofenceInDatabase(geofenceId);
        if (bo.isSuccess()) {
            return geofenceDBDataSource.obtainGeofenceEvent(bo.getData());
        }
        return new BusinessObject<>(null, bo.getBusinessError());
    }

    @Override
    public BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence) {
        return geofenceDBDataSource.updateGeofenceWithActionId(geofence);
    }

    @Override
    public BusinessObject<OrchextraGeofence> storeGeofenceEvent(String geofenceId) {
        BusinessObject<OrchextraGeofence> bo = obtainSavedGeofenceInDatabase(geofenceId);
        if (bo.isSuccess()) {
            return geofenceDBDataSource.storeGeofenceEvent(bo.getData());
        }
        return new BusinessObject<>(null, bo.getBusinessError());
    }

    @Override
    public BusinessObject<OrchextraGeofence> deleteGeofenceEvent(String geofenceId) {
        BusinessObject<OrchextraGeofence> bo = obtainSavedGeofenceInDatabase(geofenceId);
        if (bo.isSuccess()) {
            return geofenceDBDataSource.deleteGeofenceEvent(bo.getData());
        }
        return new BusinessObject<>(null, bo.getBusinessError());
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
