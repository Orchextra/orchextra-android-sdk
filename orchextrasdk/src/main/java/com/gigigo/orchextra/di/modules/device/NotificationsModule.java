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

package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.gigigo.orchextra.device.notifications.AndroidNotificationBuilder;
import com.gigigo.orchextra.device.notifications.BackgroundNotificationBuilderImpl;
import com.gigigo.orchextra.device.notifications.ForegroundNotificationBuilderImpl;
import com.gigigo.orchextra.device.notifications.NotificationDispatcher;
import com.gigigo.orchextra.device.notifications.NotificationDispatcherImpl;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidNotificationMapper;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.notifications.NotificationBehavior;
import com.gigigo.orchextra.device.notifications.NotificationBehaviorImpl;

import orchextra.javax.inject.Singleton;

import orchextra.dagger.Module;
import orchextra.dagger.Provides;


@Module
public class NotificationsModule {

  @Provides
  @Singleton
  ForegroundNotificationBuilderImpl provideForegroundNotificationBuilderImp(ContextProvider contextProvider) {
    return new ForegroundNotificationBuilderImpl(contextProvider);
  }

  @Provides
  @Singleton NotificationBehavior provideNotificationBehavior(AppRunningMode appRunningMode,
      ForegroundNotificationBuilderImpl foregroundNotificationBuilderImpl,
      BackgroundNotificationBuilderImpl backgroundNotificationBuilderImpl,
      ContextProvider contextProvider) {
    return new NotificationBehaviorImpl(appRunningMode, foregroundNotificationBuilderImpl,
            backgroundNotificationBuilderImpl, contextProvider);
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
  BackgroundNotificationBuilderImpl provideBackgroundNotificationBuilderImp(AndroidNotificationBuilder androidNotificationBuilder,
                                                                            AndroidBasicActionMapper androidBasicActionMapper) {
    return new BackgroundNotificationBuilderImpl(androidNotificationBuilder, androidBasicActionMapper);
  }

  @Provides @Singleton NotificationDispatcher provideNotificationDispatcherImpl(ActionRecovery actionRecovery){
    return new NotificationDispatcherImpl(actionRecovery);
  }

}
