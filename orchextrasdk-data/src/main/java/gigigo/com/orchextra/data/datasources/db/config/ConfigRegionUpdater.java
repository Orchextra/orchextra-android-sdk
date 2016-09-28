/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegionUpdates;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigRegionUpdater {

  private final Mapper<OrchextraRegion, RegionRealm> beaconRealmMapper;

  public ConfigRegionUpdater(Mapper<OrchextraRegion, RegionRealm> beaconRealmMapper) {
    this.beaconRealmMapper = beaconRealmMapper;
  }

  public OrchextraRegionUpdates saveRegions(Realm realm, List<OrchextraRegion> regions) {
    List<OrchextraRegion> newRegions = new ArrayList<>();
    List<OrchextraRegion> deleteRegions = new ArrayList<>();

    List<String> used = new ArrayList<>();

    if (regions != null) {
      addOrUpdateRegion(realm, newRegions, used, regions);
      deleteRegions = removeUnusedRegions(realm, used);
    }

    return new OrchextraRegionUpdates(newRegions, deleteRegions);
  }

  private void addOrUpdateRegion(Realm realm, List<OrchextraRegion> newRegions, List<String> used,
      List<OrchextraRegion> regions) {
    for (OrchextraRegion region : regions) {
      RegionRealm newRegion = beaconRealmMapper.modelToExternalClass(region);
      RealmResults<RegionRealm> regionRealm =
          realm.where(RegionRealm.class).equalTo("code", region.getCode()).findAll();

      if (!checkRegionAreEquals(regionRealm, newRegion)) {
        newRegions.add(region);
        realm.copyToRealmOrUpdate(newRegion);
      }

      used.add(region.getCode());
    }
  }

  private List<OrchextraRegion> removeUnusedRegions(Realm realm, List<String> used) {
    List<OrchextraRegion> deleteRegions = new ArrayList<>();

    List<String> beaconsToDelete = new ArrayList<>();
    RealmResults<RegionRealm> all = realm.where(RegionRealm.class).findAll();
    for (RegionRealm regionRealm : all) {
      if (!used.contains(regionRealm.getCode())) {
        deleteRegions.add(beaconRealmMapper.externalClassToModel(regionRealm));
        beaconsToDelete.add(regionRealm.getCode());
      }
    }
    for (String code : beaconsToDelete) {
      RealmResults<RegionRealm> regionRealm =
          realm.where(RegionRealm.class).equalTo("code", code).findAll();
      if (regionRealm.size() > 0) {
        regionRealm.first().deleteFromRealm();
      }
    }

    return deleteRegions;
  }

  private boolean checkRegionAreEquals(RealmResults<RegionRealm> beaconRealm,
      RegionRealm newBeacon) {
    if (beaconRealm.size() == 0 || newBeacon == null) {
      return false;
    }
    RegionRealm beacon = beaconRealm.first();
    return beacon.getCode().equals(newBeacon.getCode()) &&
        beacon.getMinor() == newBeacon.getMinor() &&
        beacon.getMajor() == newBeacon.getMajor() &&
        beacon.getUuid().equals(newBeacon.getUuid()) &&
        beacon.isActive() == newBeacon.isActive();
  }

  public void removeRegions(Realm realm) {
    if (realm != null) {
      realm.delete(RegionRealm.class);
    }
  }
}
