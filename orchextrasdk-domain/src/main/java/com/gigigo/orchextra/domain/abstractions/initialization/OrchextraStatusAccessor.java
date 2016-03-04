package com.gigigo.orchextra.domain.abstractions.initialization;

import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public interface OrchextraStatusAccessor {
  boolean isInitialized() throws NullPointerException;
  boolean isStarted() throws NullPointerException;
  Session getSession();
  Crm getCrm();
  void loadOrchextraStatus();
  void updateOrchextraStatus(OrchextraStatus orchextraStatus);
}
