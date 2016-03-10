package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.domain.dataprovider.OrchextraStatusDataProvider;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraServiceStatus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/3/16.
 *
 * Consider this module for fastest domain service operations, they can't take more than 300 ms
 * beacuse all the executions are inside ui thread.
 *
 */
@Module
public class FastDomainServicesModule {

  @Provides @Singleton LoadOrchextraServiceStatus provideLoadOrchextraServiceStatus(
      OrchextraStatusDataProvider orchextraStatusDataProvider){
    return new LoadOrchextraServiceStatus(orchextraStatusDataProvider);
  }


  @Provides @Singleton UpdateOrchextraServiceStatus provideUpdateOrchextraServiceStatus(
      OrchextraStatusDataProvider orchextraStatusDataProvider){
    return new UpdateOrchextraServiceStatus(orchextraStatusDataProvider);
  }
}
