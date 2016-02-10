package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface BeaconsModuleProvider {
  BeaconScanner provideBeaconScanner();
}
