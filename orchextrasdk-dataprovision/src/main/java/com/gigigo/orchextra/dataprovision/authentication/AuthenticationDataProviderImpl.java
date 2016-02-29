package com.gigigo.orchextra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public class AuthenticationDataProviderImpl implements AuthenticationDataProvider {

  private final AuthenticationDataSource authenticationDataSource;
  private final SessionDBDataSource sessionDBDataSource;

  public AuthenticationDataProviderImpl(AuthenticationDataSource authenticationDataSource,
      SessionDBDataSource sessionDBDataSource) {
    this.authenticationDataSource = authenticationDataSource;
    this.sessionDBDataSource = sessionDBDataSource;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(Credentials credentials) {
    BusinessObject<SdkAuthData> deviceToken = sessionDBDataSource.getDeviceToken();

    if (!deviceToken.isSuccess() || deviceToken.getData().isExpired()){
      deviceToken = authenticationDataSource.authenticateSdk(credentials);

      if (deviceToken.isSuccess()){
        sessionDBDataSource.saveSdkAuthResponse(deviceToken.getData());
      }
    }

    return deviceToken;
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(Credentials credentials, String crmId) {
    BusinessObject<ClientAuthData> sessionToken = sessionDBDataSource.getSessionToken();

    if (!sessionToken.isSuccess() || sessionToken.getData() == null || sessionToken.getData().isExpired()) {

      sessionToken = authenticationDataSource.authenticateUser(credentials);

      if (sessionToken.isSuccess()) {
        sessionDBDataSource.saveClientAuthResponse(sessionToken.getData());
        saveCrmId(crmId);
      }
    }

    return sessionToken;
  }

  private void saveCrmId(String crmId) {
    if (crmId != null) {
      Crm crm = sessionDBDataSource.getCrm().getData();

      if (crm == null) {
        crm = new Crm();
      }

      crm.setCrmId(crmId);
      sessionDBDataSource.saveUser(crm);
    }
  }

  @Override
  public BusinessObject<Crm> retrieveCrm() {
    return sessionDBDataSource.getCrm();
  }

  @Override
  public BusinessObject<ClientAuthData> getCredentials() {
    return sessionDBDataSource.getSessionToken();
  }

  @Override public BusinessObject<String> getCrmID() {

    BusinessObject<Crm> bo = sessionDBDataSource.getCrm();

    if (bo.isSuccess()){
      return new BusinessObject<>(bo.getData().getCrmId(), bo.getBusinessError());
    }

    return new BusinessObject<>(null, bo.getBusinessError());
  }

  @Override public void storeCrmId(String crmId) {
    sessionDBDataSource.saveUserId(crmId);
  }
}
