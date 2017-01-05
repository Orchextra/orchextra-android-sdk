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

package gigigo.com.orchextra.data.datasources.db.proximity;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.proximity.datasource.ProximityDBDataSource;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;

public class ProximityDBDataSourceImpl implements ProximityDBDataSource {

    private final Context context;
    private final ProximityEventsUpdater proximityEventsUpdater;
    private final RegionEventsReader regionEventsReader;
    private final RealmDefaultInstance realmDefaultInstance;
    private final OrchextraLogger orchextraLogger;

    public ProximityDBDataSourceImpl(Context context, ProximityEventsUpdater proximityEventsUpdater,
                                     RegionEventsReader regionEventsReader,
                                     RealmDefaultInstance realmDefaultInstance,
                                     OrchextraLogger orchextraLogger) {

        this.context = context;
        this.proximityEventsUpdater = proximityEventsUpdater;
        this.regionEventsReader = regionEventsReader;
        this.realmDefaultInstance = realmDefaultInstance;
        this.orchextraLogger = orchextraLogger;
        //fixme realm
    }

    @Override
    public BusinessObject<OrchextraRegion> obtainRegionEvent(OrchextraRegion orchextraRegion) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        try {
            OrchextraRegion region = regionEventsReader.obtainRegionEvent(realm, orchextraRegion);
            return new BusinessObject<>(region, BusinessError.createOKInstance());
        } catch (Exception e) {
            return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<OrchextraRegion> storeRegionEvent(OrchextraRegion orchextraRegion) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        try {
            OrchextraRegion region = proximityEventsUpdater.storeRegionEvent(realm, orchextraRegion);
            return new BusinessObject<>(region, BusinessError.createOKInstance());
        } catch (Exception e) {
            return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<OrchextraRegion> deleteRegionEvent(OrchextraRegion orchextraRegion) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        try {
            OrchextraRegion region = proximityEventsUpdater.deleteRegionEvent(realm, orchextraRegion);
            return new BusinessObject<>(region, BusinessError.createOKInstance());
        } catch (Exception e) {
            return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        try {
            OrchextraBeacon storedBeacon = proximityEventsUpdater.storeBeaconEvent(realm, beacon);
            return new BusinessObject<>(storedBeacon, BusinessError.createOKInstance());
        } catch (Exception e) {
            return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    /**
     * @param requestTime
     */
    @Override
    public void deleteAllBeaconsInListWithTimeStampt(int requestTime) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        proximityEventsUpdater.deleteAllBeaconsInListWithTimeStampt(realm, requestTime);
        if (realm != null) {
            realm.close();
        }
    }

    @Override
    public BusinessObject<List<OrchextraBeacon>> getNotStoredBeaconEvents(
            List<OrchextraBeacon> list) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            List<String> codes = proximityEventsUpdater.obtainStoredEventBeaconCodes(realm, list);
            List<OrchextraBeacon> beacons = OrchextraBeacon.removeFromListElementsWithCodes(list, codes);

            if (!beacons.isEmpty()) {
                orchextraLogger.log("This ranging event has not discovered any new beacon event to be sent");
            } else {
                orchextraLogger.log("This ranging event has " + beacons.size() + " events to be sent");
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
                    proximityEventsUpdater.addActionToRegion(realm, orchextraRegion);

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
