package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.notifications.NotificationDispatcher;
import com.gigigo.orchextra.di.modules.control.ControlModule;
import com.gigigo.orchextra.di.modules.device.DelegateModule;
import com.gigigo.orchextra.di.modules.device.DeviceModule;
import com.gigigo.orchextra.domain.abstractions.actions.CustomSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraCompletionCallback;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureStatus;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.initalization.features.FeatureList;
import com.gigigo.orchextra.domain.interactors.actions.CustomSchemeReceiverContainer;
import com.gigigo.orchextra.domain.lifecycle.AppRunningModeImp;
import com.gigigo.orchextra.sdk.application.applifecycle.AppStatusEventsListenerImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.ContextProviderImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraContextProvider;
import com.gigigo.orchextra.sdk.model.OrcGenderConverter;
import com.gigigo.orchextra.sdk.model.OrcUserToCrmConverter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = { ControlModule.class, DeviceModule.class, DelegateModule.class })
public class OrchextraModule {

  private final Context context;
  private final OrchextraCompletionCallback orchextraCompletionCallback;

  private CustomSchemeReceiverContainer customSchemeReceiverContainer;

  public OrchextraModule(Context context, OrchextraCompletionCallback orchextraCompletionCallback) {
    this.context = context;
    this.orchextraCompletionCallback = orchextraCompletionCallback;
  }

  @Provides
  @Singleton
  OrchextraActivityLifecycle provideOrchextraActivityLifecycle(
          AppRunningMode appRunningMode, OrchextraContextProvider contextProvider,
          AppStatusEventsListener appStatusEventsListener,
          NotificationDispatcher notificationDispatcher) {

    OrchextraActivityLifecycle orchextraActivityLifecycle =
            new OrchextraActivityLifecycle(appStatusEventsListener, notificationDispatcher);
    contextProvider.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
    appRunningMode.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
    return orchextraActivityLifecycle;
  }

  @Provides
  @Singleton
  ContextProvider provideContextProvider() {
    return new ContextProviderImpl(context.getApplicationContext());
  }

  @Provides
  @Singleton
  OrchextraContextProvider provideOrchextraContextProvider(
          ContextProvider contextProvider) {
    return (OrchextraContextProvider) contextProvider;
  }

  @Provides
  @Singleton
  AppStatusEventsListener provideAppStatusEventsListener(
          ForegroundTasksManager foregroundTasksManager) {
    return new AppStatusEventsListenerImpl(context, foregroundTasksManager);
  }

  @Provides
  @Singleton
  AppRunningMode provideAppRunningMode() {
    return new AppRunningModeImp();
  }

  @Singleton
  @Provides
  FeatureList provideFeatureList() {
    return new FeatureList(orchextraCompletionCallback);
  }

  @Singleton
  @Provides
  FeatureListener provideFeatureListener(FeatureList featureList) {
    return featureList;
  }

  @Singleton
  @Provides
  FeatureStatus provideFeatureStatus(FeatureList featureList) {
    return featureList;
  }

  @Singleton
  @Provides
  CustomSchemeReceiverContainer provideCustomSchemeReceiverContainer() {
    customSchemeReceiverContainer = new CustomSchemeReceiverContainer();
    return customSchemeReceiverContainer;
  }

  @Singleton @Provides OrchextraModule provideOrchextraModule(){
    return this;
  }

  @Singleton @Provides OrcGenderConverter provideOrcGenderConverter() {
    return new OrcGenderConverter();
  }

  @Singleton @Provides OrcUserToCrmConverter provideOrcUserToCrmConverter(OrcGenderConverter orcGenderConverter) {
    return new OrcUserToCrmConverter(orcGenderConverter);
  }

  public void setCustomSchemeReceiver(CustomSchemeReceiver customSchemeReceiver) {
    customSchemeReceiverContainer.setCustomSchemeReceiver(customSchemeReceiver);
  }
}
