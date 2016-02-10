package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.bluetooth.BluetoothAvailabilityImpl;
import com.gigigo.orchextra.device.bluetooth.BluetoothStatusInfoImpl;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothAvailability;
import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatusInfo;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.altbeacon.beacon.BeaconManager;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module(includes = BeaconsModule.class)
public class BluetoothModule {

  @Provides @Singleton
  BluetoothAvailability provideBluetoothAvailability(BeaconManager beaconManager){
    return new BluetoothAvailabilityImpl(beaconManager);
  }

  @Provides @Singleton BluetoothStatusInfo provideBluetoothStatusInfo(BluetoothAvailability bluetoothAvailability,
      PermissionChecker permissionChecker, ContextProvider contextProvider, AppRunningMode appRunningMode,
      FeatureListener featureListener){
    return new BluetoothStatusInfoImpl(permissionChecker, bluetoothAvailability, contextProvider,
        appRunningMode, featureListener);
  }

}
