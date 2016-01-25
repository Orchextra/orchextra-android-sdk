package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.orchextra.android.applifecycle.DeviceRunningModeTypeImp;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.android.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.android.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.android.notifications.BackgroundNotificationBuilderImp;
import com.gigigo.orchextra.android.notifications.ForegroundNotificationBuilderImp;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcherImpl;
import com.gigigo.orchextra.domain.notifications.NotificationBehavior;
import com.gigigo.orchextra.domain.notifications.NotificationBehaviorImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {InteractorsModule.class, DomainModule.class})
public class OrchextraModule {

  private Context context;

  public OrchextraModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Context provideApplicationContext(){
    return context;
  }

  @Provides
  @Singleton
  ForegroundNotificationBuilderImp provideForegroundNotificationBuilderImp() {
    return new ForegroundNotificationBuilderImp(context);
  }

  @Provides
  @Singleton
  NotificationBehavior provideNotificationBehavior(DeviceRunningModeType deviceRunningModeType,
                                                   ForegroundNotificationBuilderImp foregroundNotificationBuilderImp,
                                                   BackgroundNotificationBuilderImp backgroundNotificationBuilderImp) {
    return new NotificationBehaviorImp(deviceRunningModeType, foregroundNotificationBuilderImp, backgroundNotificationBuilderImp);
  }

  @Provides
  @Singleton
  ActionDispatcher provideActionDispatcher(NotificationBehavior notificationBehavior) {
    return new ActionDispatcherImpl(notificationBehavior);
  }

  @Provides @Singleton
  DeviceRunningModeType provideDeviceRunningModeType() {
    return new DeviceRunningModeTypeImp();
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
  @Singleton
  OrchextraActivityLifecycle provideOrchextraActivityLifecycle(BackgroundNotificationBuilderImp backgroundNotificationBuilderImp) {
    return new OrchextraActivityLifecycle(context, backgroundNotificationBuilderImp);
  }

  @Provides
  @Singleton
  ContextProvider provideContextProvider(OrchextraActivityLifecycle orchextraActivityLifecycle) {
    return orchextraActivityLifecycle;
  }
}
