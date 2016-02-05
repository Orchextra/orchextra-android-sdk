package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.AndroidPermissionCheckerImpl;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.android.applifecycle.AppRunningModeImp;
import com.gigigo.orchextra.android.applifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.android.applifecycle.AppStatusEventsListenerImpl;
import com.gigigo.orchextra.android.applifecycle.ContextProviderImpl;
import com.gigigo.orchextra.android.applifecycle.ForegroundTasksManager;
import com.gigigo.orchextra.android.applifecycle.ForegroundTasksManagerImpl;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.android.beacons.BeaconScanner;
import com.gigigo.orchextra.android.beacons.actions.ActionsScheduler;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerController;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerControllerImpl;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.android.beacons.actions.ActionsSchedulerPersistorNullImpl;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.android.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.android.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.android.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.android.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcherImpl;
import com.gigigo.orchextra.domain.interactors.actions.ActionExecution;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.notifications.NotificationBehaviorImp;

import com.gigigo.orchextra.initalization.FeatureList;
import com.gigigo.orchextra.initalization.FeatureListener;
import com.gigigo.orchextra.initalization.FeatureStatus;
import com.gigigo.orchextra.initalization.OrchextraCompletionCallback;
import com.gigigo.orchextra.initalization.OrchextraContextProvider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {InteractorsModule.class, DomainModule.class, BeaconsModule.class})
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
  BrowserActionExecutor provideBrowserActionExecutor() {
    return new BrowserActionExecutor(context);
  }

  @Provides
  @Singleton
  WebViewActionExecutor provideWebViewActionExecutor() {
    return new WebViewActionExecutor(context);
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

  @Singleton @Provides ActionsScheduler provideActionsScheduler(ContextProvider contextProvider, FeatureListener
      featureListener){
    return new ActionsSchedulerGcmImpl(contextProvider.getApplicationContext(), featureListener);
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
  @Singleton PermissionChecker providesPermissionChecker(ContextProvider contextProvider){
    return new AndroidPermissionCheckerImpl(contextProvider.getApplicationContext(), contextProvider);
  }
}
