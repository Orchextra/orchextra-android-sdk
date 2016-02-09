package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
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
    ConfigInfoResultUpdater provideConfigInfoResultUpdater(Mapper<OrchextraRegion, BeaconRealm> beaconRealmMapper,
                                                           Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                           Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
                                                           Mapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigInfoResultUpdater(beaconRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper);
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
