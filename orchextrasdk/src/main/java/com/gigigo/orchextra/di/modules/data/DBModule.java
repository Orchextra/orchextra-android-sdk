package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.di.qualifiers.RealmMapperBeaconRegion;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.vo.Theme;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.db.auth.SessionReader;
import gigigo.com.orchextra.data.datasources.db.auth.SessionUpdater;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsReader;
import gigigo.com.orchextra.data.datasources.db.beacons.BeaconEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigBeaconUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigGeofenceUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultReader;
import gigigo.com.orchextra.data.datasources.db.config.ConfigInfoResultUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigThemeUpdater;
import gigigo.com.orchextra.data.datasources.db.config.ConfigVuforiaUpdater;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsReader;
import gigigo.com.orchextra.data.datasources.db.geofences.GeofenceEventsUpdater;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceEventRealm;
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
    ConfigBeaconUpdater provideConfigBeaconUpdater(
        @RealmMapperBeaconRegion Mapper<OrchextraRegion, BeaconRegionRealm> beaconRegionRealmMapper) {
        return new ConfigBeaconUpdater(beaconRegionRealmMapper);
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

  @Singleton
  @Provides
    GeofenceEventsUpdater provideGeofenceEventsUpdater(
          Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {
      return new GeofenceEventsUpdater(geofenceEventRealmMapper);
  }

  @Singleton
  @Provides
  GeofenceEventsReader provideGeofenceEventsReader(
          Mapper<OrchextraGeofence, GeofenceEventRealm> geofenceEventRealmMapper) {
      return new GeofenceEventsReader(geofenceEventRealmMapper);
  }
}
