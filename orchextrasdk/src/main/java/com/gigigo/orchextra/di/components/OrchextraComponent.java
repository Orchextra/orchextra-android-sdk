package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.device.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.device.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.device.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.di.components.providers.BeaconsModuleProvider;
import com.gigigo.orchextra.di.components.providers.DomainModuleProvider;
import com.gigigo.orchextra.di.components.providers.InteractorsModuleProvider;
import com.gigigo.orchextra.di.components.providers.OrchextraModuleProvider;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent extends OrchextraModuleProvider, DomainModuleProvider,
    InteractorsModuleProvider, BeaconsModuleProvider {

    //TODO delete all this methods
    ForegroundNotificationBuilderImp provideForegroundNotificationBuilderImp();

    NotificationBehavior provideNotificationBehavior();

    ActionDispatcher provideActionDispatcher();

    AndroidNotificationBuilder provideAndroidNotificationBuilder();

    AndroidNotificationMapper provideAndroidNotificationMapper();

    AndroidBasicActionMapper provideAndroidBasicActionMapper();

    BackgroundNotificationBuilderImp provideBackgroundNotificationBuilderImp();

    void injectOrchextra(Orchextra orchextra);
}
