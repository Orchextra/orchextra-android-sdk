package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.domain.entities.OrchextraGeofence;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.BeaconRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.GeofenceRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.KeyWordRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
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
    @Provides
    RealmMapper<OrchextraRegion, BeaconRealm> provideRealmMapperBeaconRealm(KeyWordRealmMapper keyWordRealmMapper) {
        return new BeaconRealmMapper(keyWordRealmMapper);
    }

    @Singleton
    @Provides
    RealmMapper<OrchextraPoint, RealmPoint> provideRealmMapperRealmPoint() {
        return new RealmPointMapper();
    }

    @Singleton
    @Provides
    RealmMapper<OrchextraGeofence, GeofenceRealm> provideRealmMapperGeofenceRealm(RealmMapper<OrchextraPoint, RealmPoint> realmPointRealmMapper,
                                                                         KeyWordRealmMapper keyWordRealmMapper) {
        return new GeofenceRealmMapper(realmPointRealmMapper, keyWordRealmMapper);
    }

    @Singleton
    @Provides
    RealmMapper<Vuforia, VuforiaRealm> provideRealmMapperVuforiaRealm() {
        return new VuforiaRealmMapper();
    }

    @Singleton
    @Provides
    RealmMapper<Theme, ThemeRealm> provideRealmMapperThemeRealm() {
        return new ThemeRealmMapper();
    }
}
