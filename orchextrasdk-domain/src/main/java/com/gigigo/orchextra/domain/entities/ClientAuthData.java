package com.gigigo.orchextra.domain.entities;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class ClientAuthData {

  private final String projectId;
  private final String userId;
  private final String value;
  private final int expiresIn;
  private final Date expiresAt;

  public ClientAuthData(String projectId, String userId, String value, int expiresIn) {
    this.projectId = projectId;
    this.userId = userId;
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = calculateExpireTime(expiresIn);
  }

  public ClientAuthData(String projectId, String userId, String value, int expiresIn,
      Date expiresAt) {
    this.projectId = projectId;
    this.userId = userId;
    this.value = value;
    this.expiresIn = expiresIn;
    this.expiresAt = expiresAt;
  }

  private Date calculateExpireTime(int expiresIn) {
    return new Date(Calendar.getInstance().getTimeInMillis()+expiresIn);
  }

  public String getProjectId() {
    return projectId;
  }

  public String getUserId() {
    return userId;
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

}
