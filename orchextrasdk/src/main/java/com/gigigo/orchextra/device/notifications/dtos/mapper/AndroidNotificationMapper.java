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
        androidNotification.setShown(androidNotification.isShown());

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
