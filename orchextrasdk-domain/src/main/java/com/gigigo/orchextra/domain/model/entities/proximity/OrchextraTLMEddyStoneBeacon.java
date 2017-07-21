package com.gigigo.orchextra.domain.model.entities.proximity;

/**
 * Created by nubor on 11/01/2017.
 */
public class OrchextraTLMEddyStoneBeacon {

  private double temperature;
  private long battery;
  private long uptime;
  private long pducount;
  private long tlmversion;
  private String url;

  public OrchextraTLMEddyStoneBeacon(long uptime, long battery, double temperature, String url) {
    this.uptime = uptime;
    this.battery = battery;
    this.temperature = temperature;
    this.url = url;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public long getBattery() {
    return battery;
  }

  public void setBattery(long battery) {
    this.battery = battery;
  }

  public long getUptime() {
    return uptime;
  }

  public void setUptime(long uptime) {
    this.uptime = uptime;
  }

  public long getPducount() {
    return pducount;
  }

  public void setPducount(long pducount) {
    this.pducount = pducount;
  }

  public long getTlmversion() {
    return tlmversion;
  }

  public void setTlmversion(long tlmversion) {
    this.tlmversion = tlmversion;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
