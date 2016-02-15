package com.gigigo.orchextra.di.modules.domain;

import com.gigigo.orchextra.di.qualifiers.ActionsErrorChecker;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.services.actions.EventUpdaterService;
import com.gigigo.orchextra.domain.services.actions.GetActionService;
import com.gigigo.orchextra.domain.services.actions.ScheduleActionService;
import com.gigigo.orchextra.domain.services.actions.TriggerActionsFacadeService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.auth.AuthenticationServiceImpl;
import com.gigigo.orchextra.domain.services.config.ConfigService;
import com.gigigo.orchextra.domain.services.proximity.BeaconCheckerService;
import com.gigigo.orchextra.domain.services.proximity.ObtainRegionsService;
import com.gigigo.orchextra.domain.services.proximity.RegionCheckerService;
import com.gigigo.orchextra.domain.services.triggers.TriggerService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/2/16.
 */
@Module
public class DomainServicesModule {

  @Provides @Singleton AuthenticationService provideAuthService(
      AuthenticationDataProvider authDataProvider,
      DeviceDetailsProvider deviceDetailsProvider, Session session){

    return new AuthenticationServiceImpl(authDataProvider, deviceDetailsProvider, session);
  }

  @Provides @Singleton BeaconCheckerService provideBeaconCheckerService(
      ProximityLocalDataProvider proximityLocalDataProvider,
      ConfigDataProvider configDataProvider){

    return new BeaconCheckerService(proximityLocalDataProvider, configDataProvider);
  }

  @Provides @Singleton RegionCheckerService provideRegionCheckerService(
      ProximityLocalDataProvider proximityLocalDataProvider,
      ConfigDataProvider configDataProvider){

    return new RegionCheckerService(proximityLocalDataProvider);
  }

  @Provides @Singleton EventUpdaterService provideEventUpdaterService(
      ProximityLocalDataProvider proximityLocalDataProvider){

    return new EventUpdaterService(proximityLocalDataProvider);
  }

  @Provides @Singleton GetActionService provideGetActionService(
      ActionsDataProvider actionsDataProvider,
      @ActionsErrorChecker ServiceErrorChecker serviceErrorChecker){

    return new GetActionService(actionsDataProvider,serviceErrorChecker);
  }

  @Provides @Singleton ScheduleActionService provideScheduleActionService(
      ActionsSchedulerController actionsSchedulerController){
    return new ScheduleActionService(actionsSchedulerController);
  }

  @Provides @Singleton TriggerService provideTriggerService(AppRunningMode appRunningMode){
    return new TriggerService(appRunningMode);
  }

  @Provides @Singleton TriggerActionsFacadeService provideTriggerActionsFacadeService(
      TriggerService triggerService, GetActionService getActionService,
      ScheduleActionService scheduleActionService){
    return new TriggerActionsFacadeService(triggerService, getActionService, scheduleActionService);
  }

  @Provides @Singleton ConfigService provideConfigService(){
    return new ConfigService();
  }

  @Provides @Singleton ObtainRegionsService provideObtainRegionsService(
      ProximityLocalDataProvider proximityLocalDataProvider){
    return new ObtainRegionsService(proximityLocalDataProvider);
  }



}
