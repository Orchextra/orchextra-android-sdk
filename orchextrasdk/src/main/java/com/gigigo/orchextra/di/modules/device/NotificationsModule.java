package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.device.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.device.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.device.notifications.NotificationDispatcher;
import com.gigigo.orchextra.device.notifications.NotificationDispatcherImpl;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.device.notifications.NotificationBehaviorImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
@Module
public class NotificationsModule {

  @Provides
  @Singleton
  ForegroundNotificationBuilderImp provideForegroundNotificationBuilderImp(ContextProvider contextProvider) {
    return new ForegroundNotificationBuilderImp(contextProvider);
  }

  @Provides
  @Singleton NotificationBehavior provideNotificationBehavior(AppRunningMode appRunningMode,
      ForegroundNotificationBuilderImp foregroundNotificationBuilderImp,
      BackgroundNotificationBuilderImp backgroundNotificationBuilderImp,
      ContextProvider contextProvider) {
    return new NotificationBehaviorImp(appRunningMode, foregroundNotificationBuilderImp,
            backgroundNotificationBuilderImp, contextProvider);
  }

  @Provides
  @Singleton AndroidNotificationBuilder provideAndroidNotificationBuilder(ContextProvider contextProvider) {
    return new AndroidNotificationBuilder(contextProvider.getApplicationContext());
  }

  @Provides
  @Singleton AndroidNotificationMapper provideAndroidNotificationMapper() {
    return new AndroidNotificationMapper();
  }

  @Provides
  @Singleton
  BackgroundNotificationBuilderImp provideBackgroundNotificationBuilderImp(AndroidNotificationBuilder androidNotificationBuilder,
      AndroidBasicActionMapper androidBasicActionMapper) {
    return new BackgroundNotificationBuilderImp(androidNotificationBuilder, androidBasicActionMapper);
  }

  @Provides @Singleton NotificationDispatcher provideNotificationDispatcherImpl(ActionRecovery actionRecovery){
    return new NotificationDispatcherImpl(actionRecovery);
  }

}
