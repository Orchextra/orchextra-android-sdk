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
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.OrchextraLoggerImpl;
import com.gigigo.orchextra.device.permissions.PermissionCameraImp;
import com.gigigo.orchextra.di.modules.control.ControlModule;
import com.gigigo.orchextra.di.modules.device.DelegateModule;
import com.gigigo.orchextra.di.modules.device.DeviceModule;
import com.gigigo.orchextra.di.modules.device.UiModule;
import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureStatus;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.domain.initalization.features.FeatureList;
import com.gigigo.orchextra.domain.initalization.observables.ConfigChangeObservable;
import com.gigigo.orchextra.domain.interactors.actions.CustomSchemeReceiverContainer;
import com.gigigo.orchextra.domain.lifecycle.AppRunningModeImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.AppStatusEventsListenerImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.ContextProviderImpl;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraContextProvider;
import com.gigigo.orchextra.sdk.model.CrmUserDomainToCrmUserSdkConverter;
import com.gigigo.orchextra.sdk.model.CrmUserGenderConverter;
import com.gigigo.orchextra.sdk.scanner.ScannerManager;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Singleton;

/**
 * module refers dagger module object
 */
@Module(includes = {ControlModule.class, DeviceModule.class, DelegateModule.class, UiModule.class})
public class OrchextraModule {

    private final Context context;
    private final OrchextraManagerCompletionCallback orchextraCompletionCallback;
    private final String notificationActivityClass ;
    private CustomSchemeReceiverContainer customSchemeReceiverContainer;

    public OrchextraModule(Context context, OrchextraManagerCompletionCallback orchextraCompletionCallback, String notificationActivityClass) {
        this.context = context;
        this.orchextraCompletionCallback = orchextraCompletionCallback;
        this.notificationActivityClass = notificationActivityClass;
    }

    @Provides
    @Singleton
    OrchextraActivityLifecycle provideOrchextraActivityLifecycle(
            AppRunningMode appRunningMode,
            OrchextraContextProvider contextProvider,
            AppStatusEventsListener appStatusEventsListener,
            OrchextraLogger orchextraLogger) {

        OrchextraActivityLifecycle orchextraActivityLifecycle =
                new OrchextraActivityLifecycle(appStatusEventsListener,
                        orchextraLogger,this.notificationActivityClass);
        contextProvider.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
        appRunningMode.setOrchextraActivityLifecycle(orchextraActivityLifecycle);
        return orchextraActivityLifecycle;
    }

    @Provides
    @Singleton
    ContextProvider provideContextProvider(OrchextraLogger orchextraLogger) {
        return new ContextProviderImpl(context.getApplicationContext(), orchextraLogger);
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
            OrchextraStatusAccessor orchextraStatusAccessor,
            OrchextraLogger orchextraLogger) {
        return new AppStatusEventsListenerImpl(context, foregroundTasksManager, orchextraStatusAccessor,
                orchextraLogger);
    }

    @Provides
    @Singleton
    AppRunningMode provideAppRunningMode() {
        return new AppRunningModeImpl();
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

    @Singleton
    @Provides
    OrchextraModule provideOrchextraModule() {
        return this;
    }

    @Singleton
    @Provides
    CrmUserGenderConverter provideCrmGenderConverter() {
        return new CrmUserGenderConverter();
    }

    @Singleton
    @Provides
    CrmUserDomainToCrmUserSdkConverter provideSdkUserToDomainConverter(CrmUserGenderConverter crmUserGenderConverter) {
        return new CrmUserDomainToCrmUserSdkConverter(crmUserGenderConverter);
    }

    @Singleton
    @Provides
    ScannerManager provideScannerManager(ContextProvider contextProvider, PermissionChecker androidPermission) {
        return new ScannerManager(contextProvider,androidPermission,
            new PermissionCameraImp(contextProvider.getApplicationContext()));
    }

    public void setCustomSchemeReceiver(CustomOrchextraSchemeReceiver customSchemeReceiver) {
        customSchemeReceiverContainer.setCustomSchemeReceiver(customSchemeReceiver);
    }

    @Provides
    @Singleton
    OrchextraLogger provideOrchextraLogger() {
        return new OrchextraLoggerImpl();
    }

    @Provides
    @Singleton
    ConfigChangeObservable provideConfigChangeObservable() {
        return new ConfigChangeObservable();
    }
}
