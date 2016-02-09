package com.gigigo.orchextra.di.modules.android;

import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.background.BackgroundTasksManagerImpl;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
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
