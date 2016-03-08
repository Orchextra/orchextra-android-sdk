package com.gigigo.orchextra.domain.abstractions.initialization;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public interface OrchextraStatusAccessor {

  boolean isInitialized() throws NullPointerException;
  boolean isStarted() throws NullPointerException;

  void started();
  void stopped();

  void initialize();

  //Session getSession();
  //Crm getCrm();
  //void loadOrchextraStatus();

}
