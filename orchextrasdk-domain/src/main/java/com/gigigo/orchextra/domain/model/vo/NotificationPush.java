package com.gigigo.orchextra.domain.model.vo;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class NotificationPush {

  private String token;
  private String senderId;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }
}
