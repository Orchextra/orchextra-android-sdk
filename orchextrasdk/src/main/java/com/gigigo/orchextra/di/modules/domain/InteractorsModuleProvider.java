package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.domain.interactors.beacons.BeaconEventsInteractor;
import com.gigigo.orchextra.domain.interactors.beacons.RegionsProviderInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  SaveUserInteractor provideSaveUserInteractor();
  RegionsProviderInteractor provideRegionsProviderInteractor();
  SendConfigInteractor provideSendConfigInteractor();
  BeaconEventsInteractor provideBeaconEventsInteractor();
  GeofenceInteractor provideGeofenceInteractor();
}
