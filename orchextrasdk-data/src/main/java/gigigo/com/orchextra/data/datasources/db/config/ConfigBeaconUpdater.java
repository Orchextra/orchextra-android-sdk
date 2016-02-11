package gigigo.com.orchextra.data.datasources.db.config;


import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeaconUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigBeaconUpdater {

    private final Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper;

    public ConfigBeaconUpdater(Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper) {
        this.beaconRealmMapper = beaconRealmMapper;
    }

    public OrchextraBeaconUpdates saveRegions(Realm realm, List<OrchextraRegion> regions) {
        List<OrchextraRegion> newRegions = new ArrayList<>();
        List<OrchextraRegion> updateRegions = new ArrayList<>();
        List<OrchextraRegion> deleteRegions = new ArrayList<>();

        List<String> used = new ArrayList<>();

        if (regions != null) {
            for (OrchextraRegion region : regions) {
                addOrUpdateRegion(realm, newRegions, updateRegions, used, region);
            }

            deleteRegions = removeUnusedRegions(realm, used);
        }

        return new OrchextraBeaconUpdates(newRegions, updateRegions, deleteRegions);
    }

    private void addOrUpdateRegion(Realm realm, List<OrchextraRegion> newRegions,
                                  List<OrchextraRegion> updateRegions, List<String> used, OrchextraRegion region) {
        BeaconRealm newRegion = beaconRealmMapper.modelToExternalClass(region);
        BeaconRealm regionRealm = realm.where(BeaconRealm.class).equalTo("code", region.getCode()).findFirst();
        if(regionRealm == null) {
            newRegions.add(region);

            realm.copyToRealm(newRegion);
        } else {
            boolean hasChangedRegion = !checkRegionAreEquals(regionRealm, newRegion);
            if (hasChangedRegion) {
                updateRegions.add(region);
                realm.copyToRealmOrUpdate(newRegion);
            }
        }
        used.add(region.getCode());
    }

    private List<OrchextraRegion> removeUnusedRegions(Realm realm, List<String> used) {
        List<OrchextraRegion> deleteRegions = new ArrayList<>();

        List<String> beaconsToDelete = new ArrayList<>();
        RealmResults<BeaconRealm> all = realm.where(BeaconRealm.class).findAll();
        for (BeaconRealm regionRealm : all) {
            if (!used.contains(regionRealm.getCode())) {
                deleteRegions.add(beaconRealmMapper.externalClassToModel(regionRealm));
                beaconsToDelete.add(regionRealm.getCode());
            }
        }
        for (String code : beaconsToDelete) {
            realm.where(BeaconRealm.class).equalTo("code", code).findFirst().removeFromRealm();
        }

        return deleteRegions;
    }

    private boolean checkRegionAreEquals(BeaconRealm beaconRealm, BeaconRealm newBeacon) {
        return beaconRealm.getCode().equals(newBeacon.getCode()) &&
                beaconRealm.getMinor() == newBeacon.getMinor() &&
                beaconRealm.getMajor() == newBeacon.getMajor() &&
                beaconRealm.getUuid() == newBeacon.getUuid() &&
                beaconRealm.isActive() == newBeacon.isActive() &&
                beaconRealm.getNotifyOnEntry() == newBeacon.getNotifyOnEntry() &&
                beaconRealm.getNotifyOnExit() == newBeacon.getNotifyOnExit() &&
                beaconRealm.getStayTime() == newBeacon.getStayTime();
    }
}
