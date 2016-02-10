package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigBeaconUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigGeofenceUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigThemeUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigVuforiaUpdater;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.ClientAuthRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.CrmRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthCredentialsRealmMapper;
import gigigo.com.orchextra.data.datasources.db.model.mappers.SdkAuthReamlMapper;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
@Module(includes = DBMapperModule.class)
public class DBModule {

    @Singleton
    @Provides
    SessionUpdater provideSessionUpdater(SdkAuthReamlMapper sdkAuthRealmMapper,
                                         ClientAuthRealmMapper clientAuthRealmMapper,
                                         CrmRealmMapper crmRealmMapper,
                                         SdkAuthCredentialsRealmMapper sdkCredentialsRealmMapper,
                                         ClientAuthCredentialsRealmMapper clientCredentialsRealmMapper) {
        return new SessionUpdater(sdkAuthRealmMapper, clientAuthRealmMapper, crmRealmMapper,
                sdkCredentialsRealmMapper, clientCredentialsRealmMapper);
    }

    @Singleton
    @Provides
    SessionReader provideSessionReader(SdkAuthReamlMapper sdkAuthRealmMapper,
                                       ClientAuthRealmMapper clientAuthRealmMapper, CrmRealmMapper crmRealmMapper) {
        return new SessionReader(sdkAuthRealmMapper, clientAuthRealmMapper, crmRealmMapper);
    }

    @Singleton
    @Provides
    ConfigBeaconUpdater provideConfigBeaconUpdater(RealmMapper<Beacon, BeaconRealm> beaconRealmMapper) {
        return new ConfigBeaconUpdater(beaconRealmMapper);
    }

    @Singleton
    @Provides
    ConfigGeofenceUpdater provideConfigGeofenceUpdater(RealmMapper<Geofence, GeofenceRealm> geofenceRealmMapper) {
        return new ConfigGeofenceUpdater(geofenceRealmMapper);
    }

    @Singleton
    @Provides
    ConfigVuforiaUpdater provideConfigVuforiaUpdater(RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
        return new ConfigVuforiaUpdater(vuforiaRealmMapper);
    }

    @Singleton
    @Provides
    ConfigThemeUpdater provideConfigThemeUpdater(RealmMapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigThemeUpdater(themeRealmMapper);
    }

    @Singleton
    @Provides
    ConfigInfoResultUpdater provideConfigInfoResultUpdater(ConfigBeaconUpdater configBeaconUpdater,
                                                           ConfigGeofenceUpdater configGeofenceUpdater,
                                                           ConfigVuforiaUpdater configVuforiaUpdater,
                                                           ConfigThemeUpdater configThemeUpdater) {
        return new ConfigInfoResultUpdater(configBeaconUpdater, configGeofenceUpdater, configVuforiaUpdater, configThemeUpdater);
    }

    @Singleton
    @Provides
    ConfigInfoResultReader provideConfigInfoResultReader(RealmMapper<Beacon, BeaconRealm> beaconRealmMapper,
                                                         RealmMapper<Geofence, GeofenceRealm> geofenceRealmMapper,
                                                         RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
                                                         RealmMapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigInfoResultReader(beaconRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper);
    }
}
