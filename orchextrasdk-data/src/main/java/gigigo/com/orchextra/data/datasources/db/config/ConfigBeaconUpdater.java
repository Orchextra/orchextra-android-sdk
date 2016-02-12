package gigigo.com.orchextra.data.datasources.db.config;


import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeaconUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigBeaconUpdater {

    private final Mapper<OrchextraRegion, BeaconRegionRealm> beaconRealmMapper;

    public ConfigBeaconUpdater(Mapper<OrchextraRegion, BeaconRegionRealm> beaconRealmMapper) {
        this.beaconRealmMapper = beaconRealmMapper;
    }

    public OrchextraBeaconUpdates saveRegions(Realm realm, List<OrchextraRegion> regions) {
        List<OrchextraRegion> newRegions = new ArrayList<>();
        List<OrchextraRegion> deleteRegions = new ArrayList<>();

        List<String> used = new ArrayList<>();

        if (regions != null) {
            addOrUpdateRegion(realm, newRegions, used, regions);
            deleteRegions = removeUnusedRegions(realm, used);
        }

        return new OrchextraBeaconUpdates(newRegions, deleteRegions);
    }

    private void addOrUpdateRegion(Realm realm, List<OrchextraRegion> newRegions, List<String> used, List<OrchextraRegion> regions) {
        for (OrchextraRegion region : regions) {
            BeaconRegionRealm newRegion = beaconRealmMapper.modelToExternalClass(region);
            BeaconRegionRealm regionRealm = realm.where(BeaconRegionRealm.class).equalTo("code", region.getCode()).findFirst();

            if(!checkRegionAreEquals(regionRealm, newRegion)) {
                newRegions.add(region);
                realm.copyToRealmOrUpdate(newRegion);
            }

            used.add(region.getCode());
        }
    }

    private List<OrchextraRegion> removeUnusedRegions(Realm realm, List<String> used) {
        List<OrchextraRegion> deleteRegions = new ArrayList<>();

        List<String> beaconsToDelete = new ArrayList<>();
        RealmResults<BeaconRegionRealm> all = realm.where(BeaconRegionRealm.class).findAll();
        for (BeaconRegionRealm regionRealm : all) {
            if (!used.contains(regionRealm.getCode())) {
                deleteRegions.add(beaconRealmMapper.externalClassToModel(regionRealm));
                beaconsToDelete.add(regionRealm.getCode());
            }
        }
        for (String code : beaconsToDelete) {
            realm.where(BeaconRegionRealm.class).equalTo("code", code).findFirst().removeFromRealm();
        }

        return deleteRegions;
    }

    private boolean checkRegionAreEquals(BeaconRegionRealm beaconRealm, BeaconRegionRealm newBeacon) {
        if (beaconRealm == null || newBeacon == null) {
            return false;
        }
        return beaconRealm.getCode().equals(newBeacon.getCode()) &&
                beaconRealm.getMinor() == newBeacon.getMinor() &&
                beaconRealm.getMajor() == newBeacon.getMajor() &&
                beaconRealm.getUuid() == newBeacon.getUuid() &&
                beaconRealm.isActive() == newBeacon.isActive();
    }
}
