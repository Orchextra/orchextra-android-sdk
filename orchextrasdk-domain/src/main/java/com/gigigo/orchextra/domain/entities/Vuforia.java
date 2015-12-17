package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class Vuforia {

  private String licenseKey;
  private String clientAccessKey;
  private String clientSecretKey;
  private String serverAccessKey;
  private String serverSecretKey;

  public String getLicenseKey() {
    return licenseKey;
  }

  public void setLicenseKey(String licenseKey) {
    this.licenseKey = licenseKey;
  }

  public String getClientAccessKey() {
    return clientAccessKey;
  }

  public void setClientAccessKey(String clientAccessKey) {
    this.clientAccessKey = clientAccessKey;
  }

  public String getClientSecretKey() {
    return clientSecretKey;
  }

  public void setClientSecretKey(String clientSecretKey) {
    this.clientSecretKey = clientSecretKey;
  }

  public String getServerAccessKey() {
    return serverAccessKey;
  }

  public void setServerAccessKey(String serverAccessKey) {
    this.serverAccessKey = serverAccessKey;
  }

  public String getServerSecretKey() {
    return serverSecretKey;
  }

  public void setServerSecretKey(String serverSecretKey) {
    this.serverSecretKey = serverSecretKey;
  }
}
