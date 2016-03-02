package com.gigigo.orchextra.domain.model.config;

import com.gigigo.orchextra.domain.model.entities.App;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.vo.Device;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class Config {

  private App app;
  private Device device;
  private GeoLocation geoLocation;
  private NotificationPush notificationPush;
  private Crm crm;

  public Config() {
  }

  public Config(Crm user) {
    this.crm = user;
  }

  public Config(App app, Device device, GeoLocation geoLocation, NotificationPush notificationPush,
      Crm crm) {
    this.app = app;
    this.device = device;
    this.geoLocation = geoLocation;
    this.notificationPush = notificationPush;
    this.crm = crm;
  }

  public App getApp() {
    return app;
  }

  public void setApp(App app) {
    this.app = app;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public GeoLocation getGeoLocation() {
    return geoLocation;
  }

  public void setGeoLocation(GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
  }

  public NotificationPush getNotificationPush() {
    return notificationPush;
  }

  public void setNotificationPush(NotificationPush notificationPush) {
    this.notificationPush = notificationPush;
  }

  public Crm getCrm() {
    return crm;
  }

  public void setCrm(Crm crm) {
    this.crm = crm;
  }
}
