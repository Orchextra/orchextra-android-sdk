package com.gigigo.orchextra.di.components;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.android.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.android.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.android.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.android.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent extends OrchextraModuleProvider, DomainModuleProvider,
    InteractorsModuleProvider {

    ForegroundNotificationBuilderImp provideForegroundNotificationBuilderImp();

    NotificationBehavior provideNotificationBehavior();

    ActionDispatcher provideActionDispatcher();

    DeviceRunningModeType provideDeviceRunningModeType();

    AndroidNotificationBuilder provideAndroidNotificationBuilder();

    AndroidNotificationMapper provideAndroidNotificationMapper();

    AndroidBasicActionMapper provideAndroidBasicActionMapper();

    BackgroundNotificationBuilderImp provideBackgroundNotificationBuilderImp();

    OrchextraActivityLifecycle provideOrchextraActivityLifecycle();

    ContextProvider provideContextProvider();
}
