package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;

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
    ConfigBeaconUpdater provideConfigBeaconUpdater(Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper) {
        return new ConfigBeaconUpdater(beaconRealmMapper);
    }

    @Singleton
    @Provides
    ConfigGeofenceUpdater provideConfigGeofenceUpdater(Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper) {
        return new ConfigGeofenceUpdater(geofenceRealmMapper);
    }

    @Singleton
    @Provides
    ConfigVuforiaUpdater provideConfigVuforiaUpdater(Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
        return new ConfigVuforiaUpdater(vuforiaRealmMapper);
    }

    @Singleton
    @Provides
    ConfigThemeUpdater provideConfigThemeUpdater(Mapper<Theme, ThemeRealm> themeRealmMapper) {
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
    ConfigInfoResultReader provideConfigInfoResultReader(Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper,
                                                         Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                         Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
                                                         Mapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigInfoResultReader(beaconRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper);
    }
}
