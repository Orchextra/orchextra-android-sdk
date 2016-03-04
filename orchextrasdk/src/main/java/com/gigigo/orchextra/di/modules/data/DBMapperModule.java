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
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.model.vo.Theme;

import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.OrchextraStatusRealmMapper;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconRegionEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconRegionRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.KeyWordRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmPointMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ThemeRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.VuforiaRealmMapper;


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

    @Singleton
    @Provides
    KeyWordRealmMapper provideKeyWordRealmMapper() {
        return new KeyWordRealmMapper();
    }

    @Singleton
    @Provides
    CrmRealmMapper provideCrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
        return new CrmRealmMapper(keyWordRealmMapper);
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
    Mapper<OrchextraRegion, BeaconRegionRealm> provideRealmMapperBeaconRegion() {
        return new BeaconRegionRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraRegion, BeaconRegionEventRealm> provideRealmMapperBeaconRegionEvent(
        @RealmMapperBeaconRegion Mapper<OrchextraRegion, BeaconRegionRealm> realmMapperBeaconRegion) {
        return new BeaconRegionEventRealmMapper(realmMapperBeaconRegion);
    }

    @Singleton
    @Provides
    Mapper<OrchextraBeacon, BeaconEventRealm> provideBeaconEventRealmMapper() {
        return new BeaconEventRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraPoint, RealmPoint> provideRealmMapperRealmPoint() {
        return new RealmPointMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraGeofence, GeofenceRealm> provideRealmMapperGeofenceRealm(Mapper<OrchextraPoint, RealmPoint> realmPointRealmMapper,
                                                                         KeyWordRealmMapper keyWordRealmMapper) {
        return new GeofenceRealmMapper(realmPointRealmMapper, keyWordRealmMapper);
    }

    @Singleton
    @Provides
    Mapper<Vuforia, VuforiaRealm> provideRealmMapperVuforiaRealm() {
        return new VuforiaRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<Theme, ThemeRealm> provideRealmMapperThemeRealm() {
        return new ThemeRealmMapper();
    }

    @Singleton
    @Provides
    Mapper<OrchextraGeofence, GeofenceEventRealm> provideGeofenceEventRealmMapper(Mapper<OrchextraPoint, RealmPoint> realmPointMapper) {
        return new GeofenceEventRealmMapper(realmPointMapper);
    }

    @Singleton
    @Provides
    Mapper<OrchextraStatus, OrchextraStatusRealm> provideOrchextraStatusRealm() {
        return new OrchextraStatusRealmMapper();
    }
}
