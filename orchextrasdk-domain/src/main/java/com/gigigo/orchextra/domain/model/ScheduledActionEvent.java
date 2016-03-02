package com.gigigo.orchextra.domain.model;

import com.gigigo.orchextra.domain.model.entities.proximity.ActionRelated;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public interface ScheduledActionEvent {
  String getActionRelatedId();

  boolean hasActionRelated();

  void setActionRelated(ActionRelated actionRelated);

  boolean relatedActionIsCancelable();
}
