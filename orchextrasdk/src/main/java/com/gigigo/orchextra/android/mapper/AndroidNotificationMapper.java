package com.gigigo.orchextra.android.mapper;

import com.gigigo.orchextra.android.entities.AndroidNotification;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.mappers.Mapper;

public class AndroidNotificationMapper implements Mapper<Notification, AndroidNotification> {

    @Override
    public AndroidNotification modelToAndroid(Notification notification) {
        AndroidNotification androidNotification = new AndroidNotification();

        androidNotification.setTitle(notification.getTitle());
        androidNotification.setBody(notification.getBody());
        androidNotification.setShown(androidNotification.isShown());

        return androidNotification;
    }

    @Override
    public Notification androidToModel(AndroidNotification androidNotification) {
        Notification notification = new Notification();

        notification.setTitle(androidNotification.getTitle());
        notification.setBody(androidNotification.getBody());
        notification.setShown(androidNotification.isShown());

        return notification;
    }
}
