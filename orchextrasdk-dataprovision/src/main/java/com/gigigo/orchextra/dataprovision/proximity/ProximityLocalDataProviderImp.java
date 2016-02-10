package com.gigigo.orchextra.dataprovision.proximity;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;

import java.util.List;

public class ProximityLocalDataProviderImp implements ProximityLocalDataProvider {

    private final ConfigDBDataSource configDBDataSource;

    public ProximityLocalDataProviderImp(ConfigDBDataSource configDBDataSource) {
        this.configDBDataSource = configDBDataSource;
    }

    @Override
    public BusinessObject<OrchextraBeacon> obtainConcreteBeacon(OrchextraBeacon orchextraBeacon) {
        //TODO implement
        return null;
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceByCodeFromDatabase(String geofenceId) {
        return configDBDataSource.obtainGeofenceById(geofenceId);
    }
}
