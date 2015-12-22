package com.gigigo.orchextra.domain.entities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class SdkAuthData {

  private final String value;
  private final int expiresIn;
  private final Date expiresAt;
  private final String projectId;

  public SdkAuthData(String value, int expiresIn, String projectId) {
    this.value = value;
    this.expiresIn = expiresIn;
    this.projectId = projectId;
    this.expiresAt = calculateExpireTime(expiresIn);
  }

  public SdkAuthData(String value, int expiresIn, Date expiresAt, String projectId) {
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = expiresAt;
    this.projectId = projectId;
  }

  private Date calculateExpireTime(int expiresIn) {
    return new Date(Calendar.getInstance().getTimeInMillis()+expiresIn);
  }

  public String getValue() {
    return value;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public String getProjectId() {
    return projectId;
  }

  public boolean isExpired() {
    return Calendar.getInstance().getTime().after(expiresAt);
  }
}
