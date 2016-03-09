package com.gigigo.orchextra.control.controllers.status;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.abstractions.future.FutureResult;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.abstractions.initialization.StartStatusType;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.status.OrchextraStatusInteractor;
import com.gigigo.orchextra.domain.interactors.status.StatusOperationType;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraServiceStatus;
import javax.inject.Provider;

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
 * TODO Dev note: regarding refactor, think about using state patern for managing status
 */
public class OrchextraStatusAccessorAccessorImpl implements OrchextraStatusAccessor {

  private OrchextraStatus orchextraStatus = null;

  //private final InteractorInvoker interactorInvoker;
  private final ErrorLogger errorLogger;
  private final Provider<InteractorExecution> orchextraStatusInteractorExecution;
  private final Session session;

  private final LoadOrchextraServiceStatus loadOrchextraServiceStatus;
  private final UpdateOrchextraServiceStatus updateOrchextraServiceStatus;

  public OrchextraStatusAccessorAccessorImpl(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> orchextraStatusInteractorExecution, Session session, ErrorLogger errorLogger,
      LoadOrchextraServiceStatus loadOrchextraServiceStatus,
      UpdateOrchextraServiceStatus updateOrchextraServiceStatus) {
    //this.interactorInvoker = interactorInvoker;
    this.orchextraStatusInteractorExecution = orchextraStatusInteractorExecution;
    this.session = session;
    this.errorLogger = errorLogger;

    this.loadOrchextraServiceStatus = loadOrchextraServiceStatus;
    this.updateOrchextraServiceStatus = updateOrchextraServiceStatus;
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

  private void loadOrchextraStatus() {
    InteractorResponse<OrchextraStatus> response =  loadOrchextraServiceStatus.load();
    if (!response.hasError()){
      this.orchextraStatus = response.getResult();
    }
    //if (this.orchextraStatus==null){
    //  FutureResult<OrchextraStatus> futureOrchextraStatus = new FutureResult();
    //  startAsyncOperation(StatusOperationType.LOAD_ORCHEXTRA_STATUS, futureOrchextraStatus);
    //  this.orchextraStatus = getOrchextraStatus(futureOrchextraStatus);
    //}
  }

  private void updateOrchextraStatus() {
    InteractorResponse<OrchextraStatus> response =  updateOrchextraServiceStatus.update(orchextraStatus);
    if (!response.hasError()){
      this.orchextraStatus = response.getResult();
    }

    //FutureResult<OrchextraStatus> futureOrchextraStatus = new FutureResult();
    //startAsyncOperation(StatusOperationType.UPDATE_ORCHEXTRA_STATUS, futureOrchextraStatus);
    //this.orchextraStatus = getOrchextraStatus(futureOrchextraStatus);
  }


  private void startAsyncOperation(StatusOperationType statusOperationType,
      FutureResult futureOrchextraStatus) {
    InteractorExecution interactorExecution = orchextraStatusInteractorExecution.get();
    OrchextraStatusInteractor interactor = (OrchextraStatusInteractor) interactorExecution.getInteractor();

    if (statusOperationType == StatusOperationType.LOAD_ORCHEXTRA_STATUS){
      interactor.loadData();
    }else{
      interactor.updateData(orchextraStatus);
    }

    interactor.loadData();

   // executeBeaconInteractor(interactorExecution, futureOrchextraStatus);
  }


  private OrchextraStatus getOrchextraStatus(FutureResult<OrchextraStatus> future) {
    try {
      return future.get();
    } catch (Exception e) {
      e.printStackTrace();

      if (this.orchextraStatus!=null){
        return orchextraStatus;
      }else{
        OrchextraStatus orchextraStatus = OrchextraStatus.getInstance();
        orchextraStatus.setInitialized(true);
        return orchextraStatus;
      }

    }
  }

  private void executeBeaconInteractor(InteractorExecution interactorExecution, final FutureResult futureOrchextraStatus) {
    //interactorExecution.result(new InteractorResult<OrchextraStatus>() {
    //  @Override public void onResult(OrchextraStatus result) {
    //    orchextraStatus = result;
    //    futureOrchextraStatus.onReadyFutureResult(orchextraStatus);
    //  }
    //}).error(InteractorError.class, new InteractorResult<InteractorError>() {
    //  @Override public void onResult(InteractorError result) {
    //    errorLogger.log(result.getError());
    //    futureOrchextraStatus.onErrorFutureResult(result.getError().getMessage());
    //  }
    //}).execute(interactorInvoker);
  }


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
