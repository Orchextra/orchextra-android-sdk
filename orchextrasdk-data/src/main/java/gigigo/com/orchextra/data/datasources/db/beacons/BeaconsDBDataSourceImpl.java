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

package gigigo.com.orchextra.data.datasources.db.beacons;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import java.util.List;


  @Override public void deleteAllBeaconsInListWithTimeStampt(int requestTime) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    beaconEventsUpdater.deleteAllBeaconsInListWithTimeStampt(realm, requestTime);
    if (realm != null) {
      realm.close();
    }
  }

  @Override public BusinessObject<List<OrchextraBeacon>> getNotStoredBeaconEvents(
      List<OrchextraBeacon> list) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      List<String> codes = beaconEventsUpdater.obtainStoredEventBeaconCodes(realm, list);
      List<OrchextraBeacon> beacons = OrchextraBeacon.removeFromListElementsWithCodes(list, codes);

      if (!beacons.isEmpty()) {
        GGGLogImpl.log("This ranging event has not discovered any new beacon event to be sent");
      } else {
        GGGLogImpl.log("This ranging event has " + beacons.size() + " events to be sent");
      }

      return new BusinessObject<>(beacons, BusinessError.createOKInstance());
    } catch (Exception e) {
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraRegion orchextraRegionUpdated =
          beaconEventsUpdater.addActionToRegion(realm, orchextraRegion);

      return new BusinessObject<>(orchextraRegionUpdated, BusinessError.createOKInstance());
    } catch (Exception e) {
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }
}
