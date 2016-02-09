package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.device.actions.AndroidActionDispatcher;
import com.gigigo.orchextra.device.actions.ViewActionDispatcher;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.AndroidGeofenceConverter;
import com.gigigo.orchextra.device.geolocation.geofencing.mapper.LocationMapper;
import com.gigigo.orchextra.di.modules.android.AndroidModule;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import com.gigigo.orchextra.domain.abstractions.beacons.BeaconScanner;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.lifecycle.AppRunningModeImp;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.outputs.BackThreadSpec;
import com.gigigo.orchextra.sdk.application.applifecycle.AppStatusEventsListenerImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.ContextProviderImpl;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.foreground.ForegroundTasksManagerImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.control.controllers.action.scheduler.ActionsSchedulerControllerImpl;
import com.gigigo.orchextra.device.actions.scheduler.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.control.controllers.action.scheduler.ActionsSchedulerPersistorNullImpl;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.device.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.device.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.device.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.di.modules.android.BeaconsModule;
import com.gigigo.orchextra.di.modules.domain.DomainModule;
import com.gigigo.orchextra.di.modules.domain.InteractorsModule;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsScheduler;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcherImpl;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.notifications.NotificationBehaviorImp;

import com.gigigo.orchextra.domain.initalization.features.FeatureList;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureStatus;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraCompletionCallback;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraContextProvider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {InteractorsModule.class, DomainModule.class, BeaconsModule.class,
    AndroidModule.class})
public class OrchextraModule {

  private final Context context;
  private final OrchextraCompletionCallback orchextraCompletionCallback;

  public OrchextraModule(Context context, OrchextraCompletionCallback orchextraCompletionCallback) {
    this.context = context;
    this.orchextraCompletionCallback = orchextraCompletionCallback;
  }

  @Provides
  @Singleton
  ForegroundNotificationBuilderImp provideForegroundNotificationBuilderImp() {
    return new ForegroundNotificationBuilderImp(context);
  }

  @Provides
  @Singleton
  NotificationBehavior provideNotificationBehavior(AppRunningMode appRunningMode,
                                                   ForegroundNotificationBuilderImp foregroundNotificationBuilderImp,
                                                   BackgroundNotificationBuilderImp backgroundNotificationBuilderImp) {
    return new NotificationBehaviorImp(appRunningMode, foregroundNotificationBuilderImp, backgroundNotificationBuilderImp);
  }

  @Provides
  @Singleton
  ActionDispatcher provideActionDispatcher(NotificationBehavior notificationBehavior) {
    return new ActionDispatcherImpl(null, notificationBehavior);
  }

  @Provides
  @Singleton
  AndroidNotificationBuilder provideAndroidNotificationBuilder() {
    return new AndroidNotificationBuilder(context);
  }

  @Provides
  @Singleton
  AndroidNotificationMapper provideAndroidNotificationMapper() {
    return new AndroidNotificationMapper();
  }

  @Provides
  @Singleton
  AndroidBasicActionMapper provideAndroidBasicActionMapper(AndroidNotificationMapper androidNotificationMapper) {
    return new AndroidBasicActionMapper(androidNotificationMapper);
  }

  @Provides
  @Singleton
  BackgroundNotificationBuilderImp provideBackgroundNotificationBuilderImp(AndroidNotificationBuilder androidNotificationBuilder,
                                                                           AndroidBasicActionMapper androidBasicActionMapper) {
    return new BackgroundNotificationBuilderImp(androidNotificationBuilder, androidBasicActionMapper);
  }


  @Provides
  @Singleton AppStatusEventsListener provideAppStatusEventsListener(ForegroundTasksManager foregroundTasksManager){
    return new AppStatusEventsListenerImpl(context, foregroundTasksManager);
  }


  @Provides
  @Singleton OrchextraActivityLifecycle provideOrchextraActivityLifecycle(AppRunningMode appRunningMode, OrchextraContextProvider contextProvider,
      AppStatusEventsListener appStatusEventsListener, BackgroundNotificationBuilderImp backgroundNotificationBuilderImp) {

    OrchextraActivityLifecycle orchextraActivityLifecycle = new OrchextraActivityLifecycle(appStatusEventsListener, backgroundNotificationBuilderImp);
    contextProvider.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
    appRunningMode.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
    return orchextraActivityLifecycle;
  }

  @Provides
  @Singleton ContextProvider provideContextProvider() {
    return new ContextProviderImpl(context.getApplicationContext());
  }

  @Provides
  @Singleton OrchextraContextProvider provideOrchextraContextProvider(ContextProvider contextProvider) {
    return (OrchextraContextProvider)contextProvider;
  }

  @Provides @Singleton AppRunningMode provideAppRunningMode(){
    return new AppRunningModeImp();
  }

  @Singleton @Provides ForegroundTasksManager provideBackgroundTasksManager(BeaconScanner beaconScanner){
    return  new ForegroundTasksManagerImpl(beaconScanner);
  }

  @Singleton @Provides FeatureList provideFeatureList(){
    return new FeatureList(orchextraCompletionCallback);
  }

  @Singleton @Provides FeatureListener provideFeatureListener(FeatureList featureList){
    return featureList;
  }

  @Singleton @Provides FeatureStatus provideFeatureStatus(FeatureList featureList){
    return featureList;
  }

  @Singleton @Provides ActionsScheduler provideActionsScheduler(ContextProvider contextProvider,
      FeatureListener featureListener, AndroidBasicActionMapper androidBasicActionMapper){
    return new ActionsSchedulerGcmImpl(contextProvider.getApplicationContext(),
        featureListener, androidBasicActionMapper);
  }

  @Singleton @Provides ActionsSchedulerPersistor provideActionsSchedulerPersistorNull(){
    return new ActionsSchedulerPersistorNullImpl();
  }

  @Singleton @Provides ActionsSchedulerController provideActionsSchedulerController(
      ActionsScheduler actionsScheduler, ActionsSchedulerPersistor actionsSchedulerPersistor){

    if (actionsScheduler.hasPersistence() &&
        !(actionsSchedulerPersistor instanceof ActionsSchedulerPersistorNullImpl)){
      throw new IllegalArgumentException("Param ActionsSchedulerPersistor in"
          + " ActionsSchedulerControllerImpl MUST be NullObject when ActionsScheduler "
          + "already supports persistence ");
    }

    return new ActionsSchedulerControllerImpl(actionsScheduler, actionsSchedulerPersistor);
  }

  @Provides
  @Singleton ViewActionDispatcher providesViewActionDispatcher(AndroidBasicActionMapper androidBasicActionMapper,
      ActionDispatcher actionDispatcher){
    return new AndroidActionDispatcher(actionDispatcher, androidBasicActionMapper);
  }

  @Singleton @Provides @BackThread ThreadSpec provideBackThread(){
    return new BackThreadSpec();
  }

}
