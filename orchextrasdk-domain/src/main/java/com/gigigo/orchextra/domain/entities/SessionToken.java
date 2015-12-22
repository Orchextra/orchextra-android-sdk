package com.gigigo.orchextra.domain.entities;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionToken {
  private final String token;
  private final Date expiresAt;

  public SessionToken(String token, Date expiresAt) {
    this.token = token;
    this.expiresAt = expiresAt;
  }

  public String getToken() {
    return token;
  }

  public boolean isExpired(){
    return Calendar.getInstance().getTime().after(expiresAt);
  }
}
