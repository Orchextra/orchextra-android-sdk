package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.android.beacons.BeaconScanner;
import com.gigigo.orchextra.android.service.BackgroundTasksManager;
import com.gigigo.orchextra.android.service.BackgroundTasksManagerImpl;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/1/16.
 */
@Module
public class BackgroundModule {

  @PerService @Provides BackgroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner){
    return new BackgroundTasksManagerImpl(beaconScanner);
  }

}
