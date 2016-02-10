package gigigo.com.orchextra.data.datasources.db.config;


import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;

public class ConfigBeaconUpdater {

    private final Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper;

    public ConfigBeaconUpdater(Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper) {
        this.beaconRealmMapper = beaconRealmMapper;
    }
}
