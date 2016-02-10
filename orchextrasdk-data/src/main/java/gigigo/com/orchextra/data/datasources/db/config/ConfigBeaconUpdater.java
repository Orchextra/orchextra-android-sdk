package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.Beacon;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;

public class ConfigBeaconUpdater {

    private final RealmMapper<Beacon, BeaconRealm> beaconRealmMapper;

    public ConfigBeaconUpdater(RealmMapper<Beacon, BeaconRealm> beaconRealmMapper) {
        this.beaconRealmMapper = beaconRealmMapper;
    }
}
