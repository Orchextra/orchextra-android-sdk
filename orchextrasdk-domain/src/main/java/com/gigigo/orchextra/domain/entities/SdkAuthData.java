package com.gigigo.orchextra.domain.entities;

import java.util.Date;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class SdkAuthData {

  private String value;
  private int expiresIn;
  private Date expiresAt;
  private String projectId;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
}
