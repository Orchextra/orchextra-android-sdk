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

package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.RealmMapperBeaconRegion;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.model.vo.Theme;

import gigigo.com.orchextra.data.datasources.db.proximity.ProximityEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigRegionUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigVuforiaCredentialsUpdater;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmUserRealmMapper;
import gigigo.com.orchextra.data.datasources.db.status.OrchextraStatusReader;
import gigigo.com.orchextra.data.datasources.db.status.OrchextraStatusUpdater;
import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.proximity.RegionEventsReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigGeofenceUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigThemeUpdater;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsReader;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;


@Module(includes = DBMapperModule.class)
public class DBModule {

    @Singleton
    @Provides
    SessionUpdater provideSessionUpdater(SdkAuthReamlMapper sdkAuthRealmMapper,
                                         ClientAuthRealmMapper clientAuthRealmMapper,
                                         CrmUserRealmMapper crmUserRealmMapper,
                                         SdkAuthCredentialsRealmMapper sdkCredentialsRealmMapper,
                                         ClientAuthCredentialsRealmMapper clientCredentialsRealmMapper) {
        return new SessionUpdater(sdkAuthRealmMapper, clientAuthRealmMapper, crmUserRealmMapper,
                sdkCredentialsRealmMapper, clientCredentialsRealmMapper);
    }

    @Singleton
    @Provides
    SessionReader provideSessionReader(SdkAuthReamlMapper sdkAuthRealmMapper,
                                       ClientAuthRealmMapper clientAuthRealmMapper,
                                       CrmUserRealmMapper crmUserRealmMapper,
                                       OrchextraLogger orchextraLogger) {

        return new SessionReader(sdkAuthRealmMapper, clientAuthRealmMapper, crmUserRealmMapper,
                orchextraLogger);
    }

    @Singleton
    @Provides
    ConfigRegionUpdater provideConfigBeaconUpdater(
            @RealmMapperBeaconRegion Mapper<OrchextraRegion, RegionRealm> beaconRegionRealmMapper) {
        return new ConfigRegionUpdater(beaconRegionRealmMapper);
    }

    @Singleton
    @Provides
    ConfigGeofenceUpdater provideConfigGeofenceUpdater(Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                       OrchextraLogger orchextraLogger) {
        return new ConfigGeofenceUpdater(geofenceRealmMapper, orchextraLogger);
    }

    @Singleton
    @Provides
    ConfigVuforiaCredentialsUpdater provideConfigVuforiaUpdater(Mapper<VuforiaCredentials, VuforiaCredentialsRealm> vuforiaRealmMapper) {
        return new ConfigVuforiaCredentialsUpdater(vuforiaRealmMapper);
    }

    @Deprecated
    @Singleton
    @Provides
    ConfigThemeUpdater provideConfigThemeUpdater(Mapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigThemeUpdater(themeRealmMapper);
    }

    @Singleton
    @Provides
    ConfigInfoResultUpdater provideConfigInfoResultUpdater(ConfigRegionUpdater configRegionUpdater,
                                                           ConfigGeofenceUpdater configGeofenceUpdater,
                                                           ConfigVuforiaCredentialsUpdater configVuforiaCredentialsUpdater,
                                                           ConfigThemeUpdater configThemeUpdater) {
        return new ConfigInfoResultUpdater(configRegionUpdater, configGeofenceUpdater, configVuforiaCredentialsUpdater, configThemeUpdater);
    }

    @Singleton
    @Provides
    ConfigInfoResultReader provideConfigInfoResultReader(@RealmMapperBeaconRegion Mapper<OrchextraRegion, RegionRealm> regionRealmMapper,
                                                         Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                         Mapper<VuforiaCredentials, VuforiaCredentialsRealm> vuforiaRealmMapper,
                                                         Mapper<Theme, ThemeRealm> themeRealmMapper, OrchextraLogger orchextraLogger) {
        return new ConfigInfoResultReader(regionRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper,
                orchextraLogger);
    }

    @Singleton
    @Provides
    ProximityEventsUpdater provideBeaconEventsUpdater(
            Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper,
            Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper,
            OrchextraLogger orchextraLogger
    ) {
        return new ProximityEventsUpdater(regionEventRealmMapper, beaconEventRealmMapper, orchextraLogger);
    }

    @Singleton
    @Provides
    RegionEventsReader provideBeaconEventsReader(
            Mapper<OrchextraRegion, RegionEventRealm> regionEventRealmMapper,
            OrchextraLogger orchextraLogger) {
        return new RegionEventsReader(regionEventRealmMapper, orchextraLogger);
    }

    @Singleton
    @Provides
    GeofenceEventsUpdater provideGeofenceEventsUpdater(
            Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper,
            OrchextraLogger orchextraLogger) {
        return new GeofenceEventsUpdater(geofenceEventRealmMapper, orchextraLogger);
    }

    @Singleton
    @Provides
    GeofenceEventsReader provideGeofenceEventsReader(
            Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper,
            OrchextraLogger orchextraLogger) {
        return new GeofenceEventsReader(geofenceEventRealmMapper, orchextraLogger);
    }

    @Singleton
    @Provides
    OrchextraStatusUpdater provideOrchextraStatusUpdater(
            Mapper<OrchextraStatus, OrchextraStatusRealm> orchextraStatusRealmMapper) {
        return new OrchextraStatusUpdater(orchextraStatusRealmMapper);
    }

    @Singleton
    @Provides
    OrchextraStatusReader provideOrchextraStatusReader(
            Mapper<OrchextraStatus, OrchextraStatusRealm> orchextraStatusRealmMapper,
            OrchextraLogger orchextraLogger) {
        return new OrchextraStatusReader(orchextraStatusRealmMapper, orchextraLogger);
    }
}
