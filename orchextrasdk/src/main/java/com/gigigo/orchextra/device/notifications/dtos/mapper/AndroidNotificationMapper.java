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

package com.gigigo.orchextra.device.notifications.dtos.mapper;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.device.notifications.dtos.AndroidNotification;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;

public class AndroidNotificationMapper implements Mapper<Notification, AndroidNotification> {

    @Override
    public AndroidNotification modelToExternalClass(Notification notification) {
        AndroidNotification androidNotification = new AndroidNotification();

        androidNotification.setTitle(notification.getTitle());
        androidNotification.setBody(notification.getBody());
        androidNotification.setShown(notification.isShown());

        return androidNotification;
    }

    @Override
    public Notification externalClassToModel(AndroidNotification androidNotification) {
        Notification notification = new Notification();

        notification.setTitle(androidNotification.getTitle());
        notification.setBody(androidNotification.getBody());
        notification.setShown(androidNotification.isShown());

        return notification;
    }
}
