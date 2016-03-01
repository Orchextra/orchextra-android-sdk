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

    @Override
    public BusinessObject<OrchextraGeofence> obtainSavedGeofenceInDatabase(String geofenceId) {
        return configDBDataSource.obtainGeofenceById(geofenceId);
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceEvent(OrchextraGeofence geofence) {
        return geofenceDBDataSource.obtainGeofenceEvent(geofence);
    }

    @Override
    public BusinessObject<OrchextraGeofence> updateGeofenceWithActionId(OrchextraGeofence geofence) {
        return geofenceDBDataSource.updateGeofenceWithActionId(geofence);
    }

    @Override
    public BusinessObject<OrchextraGeofence> storeGeofenceEvent(OrchextraGeofence geofence) {
        return geofenceDBDataSource.storeGeofenceEvent(geofence);
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

    @Override public void purgeOldBeaconEventsWithRequestTime(int requestTime) {
        beaconsDBDataSource.deleteAllBeaconsInListWithTimeStampt(requestTime);
    }

    @Override public BusinessObject<List<OrchextraBeacon>> getNotStoredBeaconEvents(List<OrchextraBeacon> beacons) {
        return beaconsDBDataSource.getNotStoredBeaconEvents(beacons);
    }

        @Override public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
        return beaconsDBDataSource.updateRegionWithActionId(orchextraRegion);
    }



}
