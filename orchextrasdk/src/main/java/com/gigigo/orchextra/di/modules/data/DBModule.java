package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.RealmMapperBeaconRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsReader;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
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
    ConfigInfoResultUpdater provideConfigInfoResultUpdater(@RealmMapperBeaconRegion Mapper<OrchextraRegion, BeaconRegionRealm> regionRealmMapper,
                                                           Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                           Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
                                                           Mapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigInfoResultUpdater(regionRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper);
    }

    @Singleton
    @Provides
    ConfigInfoResultReader provideConfigInfoResultReader(@RealmMapperBeaconRegion Mapper<OrchextraRegion, BeaconRegionRealm>  regionRealmMapper,
                                                         Mapper<OrchextraGeofence, GeofenceRealm> geofenceRealmMapper,
                                                         Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper,
                                                         Mapper<Theme, ThemeRealm> themeRealmMapper) {
        return new ConfigInfoResultReader(regionRealmMapper, geofenceRealmMapper, vuforiaRealmMapper, themeRealmMapper);
    }

  @Singleton
  @Provides BeaconEventsUpdater provideBeaconEventsUpdater(
      Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper,
      Mapper<OrchextraBeacon, BeaconEventRealm> beaconEventRealmMapper
  ) {
    return new BeaconEventsUpdater(regionEventRealmMapper, beaconEventRealmMapper);
  }

  @Singleton
  @Provides BeaconEventsReader provideBeaconEventsReader(
      Mapper<OrchextraRegion, BeaconRegionEventRealm> regionEventRealmMapper
  ) {
    return new BeaconEventsReader(regionEventRealmMapper);
  }


}
