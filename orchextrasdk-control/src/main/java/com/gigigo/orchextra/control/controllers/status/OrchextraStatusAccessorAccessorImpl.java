package com.gigigo.orchextra.control.controllers.status;

import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.abstractions.initialization.StartStatusType;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraServiceStatus;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 *
 * Is important to know that the aim of this class is just change the status of SDK. So
 * responsibility of perform tasks related to start, init and stop are not related to this
 * class and should be performed as a result of consults to status served here. Thinking about
 * the methods below as starters or stoppers of Orchextra operation can lead the developer to big
 * misunderstanding issues, then BE CAREFUL.
 *
 * TODO Dev note: regarding refactor, think about using state pattern for managing status
 */
public class OrchextraStatusAccessorAccessorImpl implements OrchextraStatusAccessor {

  private OrchextraStatus orchextraStatus = null;

  private final ErrorLogger errorLogger;
  private final Session session;

  private final LoadOrchextraServiceStatus loadOrchextraServiceStatus;
  private final UpdateOrchextraServiceStatus updateOrchextraServiceStatus;

  public OrchextraStatusAccessorAccessorImpl(Session session,
      LoadOrchextraServiceStatus loadOrchextraServiceStatus,
      UpdateOrchextraServiceStatus updateOrchextraServiceStatus,
      ErrorLogger errorLogger) {

    this.session = session;
    this.loadOrchextraServiceStatus = loadOrchextraServiceStatus;
    this.updateOrchextraServiceStatus = updateOrchextraServiceStatus;
    this.errorLogger = errorLogger;
  }

  @Override public void initialize() {
    loadOrchextraStatus();
    orchextraStatus.setInitialized(true);
    updateOrchextraStatus();
  }

  @Override public StartStatusType setStartedStatus(String apiKey, String apiSecret) throws RuntimeException{

    StartStatusType startStatusType = obtainCurrentStartStatus(apiKey, apiSecret);

    switch (startStatusType){
      case SDK_READY_FOR_START:
        setSDKstatusAsStarted(apiKey, apiSecret);
        return StartStatusType.SDK_READY_FOR_START;

      case SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS:
        changeSdkCredentials(apiKey, apiSecret);
        return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS;

      case SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS:
        throw new SdkAlreadyStartedException("SDK STATUS EXCEPTION; Status: "
            + startStatusType.getStringValue() + "." +" This call will be ignored");

      case SDK_WAS_NOT_INITIALIZED:
        throw new SdkNotInitializedException("SDK STATUS EXCEPTION; Status: "
            + startStatusType.getStringValue() + "." +" You "
            + "must call Orchextra.init() before calling Orchextra.start()");

      default:
        throw new SdkInitializationException("SDK STATUS EXCEPTION; Status: "
            + startStatusType.getStringValue() + "." +" Review your log system");
    }
  }

  private void changeSdkCredentials(String apiKey, String apiSecret) {
    orchextraStatus.getSession().setAppParams(apiKey, apiSecret);
    updateOrchextraStatus();
  }

  private void setSDKstatusAsStarted(String apiKey, String apiSecret) {
    session.setAppParams(apiKey,apiSecret);
    orchextraStatus.setSession(session);
    orchextraStatus.setStarted(true);
    updateOrchextraStatus();
  }

  private StartStatusType obtainCurrentStartStatus(String apiKey, String apiSecret) {

    loadOrchextraStatus();

    if (alreadyStarted()){
      return alreadyStartedType(apiKey, apiSecret);
    }else if (!orchextraStatus.isInitialized()){
      return StartStatusType.SDK_WAS_NOT_INITIALIZED;
    }else if (orchextraStatus.isInitialized()){
      return StartStatusType.SDK_READY_FOR_START;
    }else{
      return StartStatusType.UNKNOWN_START_STATUS;
    }

  }

  private StartStatusType alreadyStartedType(String startApiKey, String startApiSecret) {
    String currentApiKey = orchextraStatus.getSession().getApiKey();
    String currentApiSecret = orchextraStatus.getSession().getApiSecret();
    if (currentApiKey.equals(startApiKey) && currentApiSecret.equals(startApiSecret)){
      return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS;
    }else{
      return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS;
    }
  }

  private boolean alreadyStarted() {
    try {
      return (orchextraStatus.isStarted() && hasCredentials());
    }catch (NullPointerException e){
      return false;
    }
  }

  private boolean hasCredentials() {
    String apiKey = orchextraStatus.getSession().getApiKey();
    String apiSecret = orchextraStatus.getSession().getApiSecret();
    return (!apiKey.equals("") && !apiSecret.equals(""));
  }

  @Override public boolean isStarted() throws NullPointerException {
    loadOrchextraStatus();
    return orchextraStatus.isStarted();
  }

  @Override public void setStoppedStatus() {
    loadOrchextraStatus();
    if (orchextraStatus.isStarted()){
      orchextraStatus.setStarted(false);
      updateOrchextraStatus();
    }
  }

  // region domain services

  /*
    The following two operations were thought at the beginning to be executed in a separate thread
    using interactor executor, but we had to wait till the end of them to continue, this fact was not
    blocking the UI but was creating a dependency between threads that was making compulsory to use
    more than one threads in the thread poll. Regarding both operations are really fast, they are
    not always performed, and code is much cleaner, has been taken the decision of perform them
    on Ui thread. The previous implementation was at splitStartInit branch with a tag named
    Load_update_OxStatus_futures. Consider recovery of thar implementation and make it some
    improvement if this operations become much more expensive.
   */

  private void loadOrchextraStatus() {
    InteractorResponse<OrchextraStatus> response =  loadOrchextraServiceStatus.load();
    if (!response.hasError()){
      this.orchextraStatus = response.getResult();
    }
  }

  private void updateOrchextraStatus() {
    InteractorResponse<OrchextraStatus> response =  updateOrchextraServiceStatus.update(orchextraStatus);
    if (!response.hasError()){
      this.orchextraStatus = response.getResult();
    }
  }
  //endregion

  //region TODO
  //TODO move CRM and Session management here
  //@Override public Session getSession() {
  //  //TODO
  //  return null;
  //}
  //
  //@Override public void updateSession(Session session) {
  //  //TODO
  //}
  //
  //@Override public Crm getCrm() {
  //  //TODO
  //  return null;
  //}
  //
  //@Override public void updateCrm(Crm crm) {
  //  //TODO
  //}
  // endregion

}
