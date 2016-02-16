package com.gigigo.orchextra.domain.model;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public interface ScheduledActionEvent {
  String getActionRelated();
  boolean hasActionRelated();
  void setActionRelated(String actionRelated);
}
