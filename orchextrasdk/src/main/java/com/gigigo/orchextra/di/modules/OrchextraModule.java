/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.notifications.NotificationDispatcher;
import com.gigigo.orchextra.di.modules.control.ControlModule;
import com.gigigo.orchextra.di.modules.device.DelegateModule;
import com.gigigo.orchextra.di.modules.device.DeviceModule;
import com.gigigo.orchextra.di.modules.device.UiModule;
import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
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
import com.gigigo.orchextra.sdk.scanner.ScannerManager;

import orchextra.javax.inject.Singleton;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;

@Module(includes = { ControlModule.class, DeviceModule.class, DelegateModule.class, UiModule.class})
public class OrchextraModule {

  private final Context context;
  private final OrchextraManagerCompletionCallback orchextraCompletionCallback;

  private CustomSchemeReceiverContainer customSchemeReceiverContainer;

  public OrchextraModule(Context context, OrchextraManagerCompletionCallback orchextraCompletionCallback) {
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
          ForegroundTasksManager foregroundTasksManager,
          OrchextraStatusAccessor orchextraStatusAccessor) {
    return new AppStatusEventsListenerImpl(context, foregroundTasksManager, orchextraStatusAccessor);
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

  @Singleton @Provides
  ScannerManager provideScannerManager(ContextProvider contextProvider) {
    return new ScannerManager(contextProvider.getApplicationContext());
  }

  public void setCustomSchemeReceiver(CustomOrchextraSchemeReceiver customSchemeReceiver) {
    customSchemeReceiverContainer.setCustomSchemeReceiver(customSchemeReceiver);
  }
}
