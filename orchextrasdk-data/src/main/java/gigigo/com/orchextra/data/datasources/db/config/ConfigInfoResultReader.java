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

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import io.realm.Realm;
import io.realm.RealmResults;


public class ConfigInfoResultReader {

    private final ExternalClassToModelMapper<RegionRealm, OrchextraRegion> regionRealmMapper;
    private final ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper;
    private final ExternalClassToModelMapper<VuforiaCredentialsRealm, VuforiaCredentials> vuforiaRealmMapper;

    private final OrchextraLogger orchextraLogger;

    @Deprecated
    public ConfigInfoResultReader(
            ExternalClassToModelMapper<RegionRealm, OrchextraRegion> regionRealmMapper,
            ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper,
            ExternalClassToModelMapper<VuforiaCredentialsRealm, VuforiaCredentials> vuforiaRealmMapper,
            OrchextraLogger orchextraLogger) {

        this.regionRealmMapper = regionRealmMapper;
        this.geofencesRealmMapper = geofencesRealmMapper;
        this.vuforiaRealmMapper = vuforiaRealmMapper;

        this.orchextraLogger = orchextraLogger;
    }

    public ConfigurationInfoResult readConfigInfo(Realm realm) {

        ConfigInfoResultRealm config = readConfigObject(realm);

        VuforiaCredentials vuforiaCredentials = vuforiaRealmMapper.externalClassToModel(readVuforiaObject(realm));

        List<OrchextraGeofence> geofences = geofencesToModel(readGeofenceObjects(realm));
        List<OrchextraRegion> regions = regionsToModel(readRegionsObjects(realm));

        ConfigurationInfoResult configurationInfoResult =
                new ConfigurationInfoResult.Builder(config.getRequestWaitTime(), geofences, regions,
                        vuforiaCredentials).build();

        orchextraLogger.log("Retrieved configurationInfoResult with properties"

                + " VuforiaCredentials :"
                + vuforiaCredentials.toString()
                + " Geofences :"
                + geofences.size()
                + " Regions :"
                + regions.size()
                + " Request Time :"
                + config.getRequestWaitTime());

        return configurationInfoResult;
    }

    private List<OrchextraRegion> regionsToModel(List<RegionRealm> regionRealms) {
        List<OrchextraRegion> regions = new ArrayList<>();
        for (RegionRealm regionRealm : regionRealms) {
            regions.add(regionRealmMapper.externalClassToModel(regionRealm));
        }
        return regions;
    }

    private List<OrchextraGeofence> geofencesToModel(List<GeofenceRealm> geofencesRealm) {
        List<OrchextraGeofence> geofences = new ArrayList<>();
        for (GeofenceRealm geofenceRealm : geofencesRealm) {
            geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
        }
        return geofences;
    }

    private ConfigInfoResultRealm readConfigObject(Realm realm) {
        ConfigInfoResultRealm configInfo = realm.where(ConfigInfoResultRealm.class).findFirst();
        if (configInfo == null) {
            configInfo = new ConfigInfoResultRealm();
        }
        return configInfo;
    }

    private VuforiaCredentialsRealm readVuforiaObject(Realm realm) {
        return realm.where(VuforiaCredentialsRealm.class).findFirst();
    }



    private List<GeofenceRealm> readGeofenceObjects(Realm realm) {
        return realm.where(GeofenceRealm.class).findAll();
    }

    private List<RegionRealm> readRegionsObjects(Realm realm) {
        return realm.where(RegionRealm.class).findAll();
    }

    public OrchextraGeofence getGeofenceById(Realm realm, String geofenceId) {

        RealmResults<GeofenceRealm> geofenceRealm =
                realm.where(GeofenceRealm.class).equalTo("code", geofenceId).findAll();

        if (geofenceRealm.size() == 0) {
            orchextraLogger.log("Not found geofence with id: " + geofenceId);
            throw new NotFountRealmObjectException();
        } else {
            orchextraLogger.log("Found geofence with id: " + geofenceId);
            return geofencesRealmMapper.externalClassToModel(geofenceRealm.first());
        }
    }

    public List<OrchextraRegion> getAllRegions(Realm realm) {
        RealmResults<RegionRealm> regionRealms = realm.where(RegionRealm.class).findAll();
        List<OrchextraRegion> regions = new ArrayList<>();

        for (RegionRealm regionRealm : regionRealms) {
            regions.add(regionRealmMapper.externalClassToModel(regionRealm));
        }

        if (regions.size() > 0) {
            orchextraLogger.log("Retrieved " + regions.size() + " beacon regions");
        } else {
            orchextraLogger.log("Not Retrieved any region");
        }

        return regions;
    }



    public List<OrchextraGeofence> getAllGeofences(Realm realm) {
        RealmResults<GeofenceRealm> geofenceRealms = realm.where(GeofenceRealm.class).findAll();
        List<OrchextraGeofence> geofences = new ArrayList<>();

        for (GeofenceRealm geofenceRealm : geofenceRealms) {
            geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
        }

        if (geofences.size() > 0) {
            orchextraLogger.log("Retrieved " + geofences.size() + " Geofences from DB");
        } else {
            orchextraLogger.log("Not Retrieved any Geofence");
        }

        return geofences;
    }
}
