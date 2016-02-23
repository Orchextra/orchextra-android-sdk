package com.gigigo.orchextra.domain.model.actions.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public class Notification {

  private String title;
  private String body;
  private boolean shown;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public boolean shouldBeShown() {
    return !shown;
  }

  public void setShown(boolean shown) {
    this.shown = shown;
  }
}
