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
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.model.vo.Theme;

import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmUserRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.OrchextraStatusRealmMapper;
import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RegionEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RegionRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.KeyWordRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmPointMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ThemeRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.VuforiaCredentialsRealmMapper;


@Module
public class DBMapperModule {

    @Singleton
    @Provides
    SdkAuthReamlMapper provideSdkAuthReamlMapper() {
        return new SdkAuthReamlMapper();
    }

    @Singleton
    @Provides
    ClientAuthRealmMapper provideClientAuthRealmMapper() {
        return new ClientAuthRealmMapper();
    }
    @Deprecated
    @Singleton
    @Provides
    KeyWordRealmMapper provideKeyWordRealmMapper() {
        return new KeyWordRealmMapper();
    }
    @Deprecated
    @Singleton
    @Provides
    CrmUserRealmMapper provideCrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
        return new CrmUserRealmMapper(keyWordRealmMapper);
    }

    @Singleton
    @Provides
    SdkAuthCredentialsRealmMapper providedkCredentialsRealmMapper() {
        return new SdkAuthCredentialsRealmMapper();
    }

    @Singleton
    @Provides
    ClientAuthCredentialsRealmMapper provideClientCredentialsRealmMapper() {
        return new ClientAuthCredentialsRealmMapper();
    }

    @Singleton
    @RealmMapperBeaconRegion
    @Provides
    Mapper<OrchextraRegion, RegionRealm> provideRealmMapperBeaconRegion() {
        return new RegionRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraRegion, RegionEventRealm> provideRealmMapperBeaconRegionEvent(
        @RealmMapperBeaconRegion Mapper<OrchextraRegion, RegionRealm> realmMapperBeaconRegion) {
        return new RegionEventRealmMapper(realmMapperBeaconRegion);
    }

    @Singleton
    @Provides
    Mapper<OrchextraBeacon, BeaconEventRealm> provideBeaconEventRealmMapper() {
        return new BeaconEventRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraLocationPoint, RealmPoint> provideRealmMapperRealmPoint() {
        return new RealmPointMapper();
    }
    @Deprecated
    @Singleton
    @Provides
    Mapper<OrchextraGeofence, GeofenceRealm> provideRealmMapperGeofenceRealm(Mapper<OrchextraLocationPoint, RealmPoint> realmPointRealmMapper,
                                                                         KeyWordRealmMapper keyWordRealmMapper) {
        return new GeofenceRealmMapper(realmPointRealmMapper, keyWordRealmMapper);
    }

    @Singleton
    @Provides
    Mapper<VuforiaCredentials, VuforiaCredentialsRealm> provideRealmMapperVuforiaRealm() {
        return new VuforiaCredentialsRealmMapper();
    }
    @Deprecated
    @Singleton
    @Provides
    Mapper<Theme, ThemeRealm> provideRealmMapperThemeRealm() {
        return new ThemeRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraGeofence, GeofenceEventRealm> provideGeofenceEventRealmMapper(Mapper<OrchextraLocationPoint, RealmPoint> realmPointMapper) {
        return new GeofenceEventRealmMapper(realmPointMapper);
    }

    @Singleton
    @Provides
    Mapper<OrchextraStatus, OrchextraStatusRealm> provideOrchextraStatusRealm() {
        return new OrchextraStatusRealmMapper();
    }
}
