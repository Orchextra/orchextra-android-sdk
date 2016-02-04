package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.android.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.android.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.android.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.android.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.di.modules.OrchextraModule;
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
