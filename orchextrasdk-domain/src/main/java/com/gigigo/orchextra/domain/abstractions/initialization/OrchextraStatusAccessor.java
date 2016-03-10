package com.gigigo.orchextra.domain.abstractions.initialization;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public interface OrchextraStatusAccessor {

  void initialize();

  /**
   * Checks sdk status, and sets started status if its able, when finished returns current status,
   * possible return status types can be StartStatusType.SDK_READY_FOR_START and
   * StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS, other status currently are
   * throwing RuntimeException because nothing relevant should be done if they are returned.
   *
   * @param apiKey credentials
   * @param apiSecret credentials
   * @return StartStatusType.SDK_READY_FOR_START in case of having to start sdk and
   * StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS in case of having to launch
   * again authentication.
   * @throws RuntimeException
   */
  StartStatusType setStartedStatus(String apiKey, String apiSecret) throws RuntimeException;

  /**
   * Checks if sdk status is already started
   *
   * @return boolean: true if started and false otherwise
   */
  boolean isStarted();

  /**
   * sets sdk status as stopped
   */
  void setStoppedStatus();

  //region TODO
  //TODO move CRM and Session management here
  //Session getSession();
  //void updateSession(Session session);

  //Crm getCrm();
  //void updateCrm(Crm crm);
  //endregion
}
