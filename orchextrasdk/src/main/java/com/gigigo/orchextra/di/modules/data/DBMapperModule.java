package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.RealmMapperBeaconRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconEventRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconRegionEventRealmMapper;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconRegionRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.KeyWordRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmPointMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ThemeRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.VuforiaRealmMapper;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
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
}
