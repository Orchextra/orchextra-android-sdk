package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.device.notifications.NotificationReceiver;
import com.gigigo.orchextra.di.scopes.PerServiceOx;

import orchextra.dagger.Component;

@PerServiceOx @Component(dependencies = {OrchextraComponent.class})
public interface  OrchextraNotificationReceiverComponent {
    void injectNotificationBroadcastReceiverComponent(NotificationReceiver notificationReceiver);
}
