package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.control.controllers.action.scheduler.ActionsSchedulerControllerImpl;
import com.gigigo.orchextra.control.controllers.action.scheduler.ActionsSchedulerPersistorNullImpl;
import com.gigigo.orchextra.device.actions.ActionExecutionImp;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.actions.AndroidActionRecovery;
import com.gigigo.orchextra.device.actions.BrowserActionExecutor;
import com.gigigo.orchextra.device.actions.ScanActionExecutor;
import com.gigigo.orchextra.device.actions.VuforiaActionExecutor;
import com.gigigo.orchextra.device.actions.WebViewActionExecutor;
import com.gigigo.orchextra.device.actions.scheduler.ActionsSchedulerGcmImpl;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsScheduler;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerController;
import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcherImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class ActionsModule {

  @Provides @Singleton
  BrowserActionExecutor provideBrowserActionExecutor(ContextProvider contextProvider) {
    return new BrowserActionExecutor(contextProvider.getApplicationContext());
  }

  @Provides
  @Singleton WebViewActionExecutor provideWebViewActionExecutor(ContextProvider contextProvider) {
    return new WebViewActionExecutor(contextProvider.getApplicationContext());
  }

  @Provides
  @Singleton ScanActionExecutor provideScanActionExecutor() {
    return new ScanActionExecutor();
  }

  @Provides
  @Singleton VuforiaActionExecutor provideVuforiaActionExecutor() {
    return new VuforiaActionExecutor();
  }

  @Provides
  @Singleton ActionExecution provideActionExecution(BrowserActionExecutor browserActionExecutor,
      WebViewActionExecutor webViewActionExecutor,
      ScanActionExecutor scanActionExecutor,
      VuforiaActionExecutor vuforiaActionExecutor) {
    return new ActionExecutionImp(browserActionExecutor, webViewActionExecutor, scanActionExecutor,
        vuforiaActionExecutor);
  }

  @Provides
  @Singleton ActionDispatcher provideActionDispatcher(ActionExecution actionExecution, NotificationBehavior notificationBehavior) {
    return new ActionDispatcherImpl(actionExecution, notificationBehavior);
  }

  @Provides
  @Singleton
  AndroidBasicActionMapper provideAndroidBasicActionMapper(AndroidNotificationMapper androidNotificationMapper) {
    return new AndroidBasicActionMapper(androidNotificationMapper);
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
  @Singleton ActionRecovery providesActionRecovery(AndroidBasicActionMapper androidBasicActionMapper,
      ActionDispatcher actionDispatcher){
    return new AndroidActionRecovery(actionDispatcher, androidBasicActionMapper);
  }
}
