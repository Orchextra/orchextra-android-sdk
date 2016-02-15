package com.gigigo.orchextra.dataprovision.authentication.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface SessionDBDataSource {

  boolean saveSdkAuthCredentials(SdkAuthCredentials sdkAuthCredentials);
  boolean saveSdkAuthResponse(SdkAuthData sdkAuthData);
  boolean saveClientAuthCredentials(ClientAuthCredentials clientAuthCredentials);
  boolean saveClientAuthResponse(ClientAuthData clientAuthData);
  boolean saveUser(Crm crm);
  BusinessObject<ClientAuthData> getSessionToken();
  BusinessObject getDeviceToken();
  BusinessObject<Crm> getCrm();

  boolean saveUserId(String crmId);
}
