package com.gigigo.orchextra.domain.entities.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class NotifFunctionalityImpl implements NotifFunctionality {

  private final Notification notification;

  public NotifFunctionalityImpl(Notification notification) {
    this.notification = notification;
  }

  @Override public Notification getNotification() {
    return notification;
  }

  @Override public boolean isSupported() {
    return (notification==null)? false : true;
  }
}
