package com.gigigo.orchextra.dataprovision.geofences;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.GeofenceDataProvider;
import com.gigigo.orchextra.domain.entities.OrchextraGeofence;

import java.util.List;

public class GeofenceDataProviderImp implements GeofenceDataProvider {

    private final ConfigDBDataSource configDBDataSource;

    public GeofenceDataProviderImp(ConfigDBDataSource configDBDataSource) {
        this.configDBDataSource = configDBDataSource;
    }

    @Override
    public BusinessObject<List<OrchextraGeofence>> obtainGeofencesFromDatabase() {
        return configDBDataSource.obtainGeofences();
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceByIdFromDatabase(String geofenceId) {
        return configDBDataSource.obtainGeofenceById(geofenceId);
    }
}
