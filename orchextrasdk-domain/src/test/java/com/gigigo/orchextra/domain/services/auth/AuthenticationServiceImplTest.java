package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.services.auth.errors.AuthenticationError;
import com.gigigo.orchextra.domain.services.auth.errors.SdkAuthError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/2/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

  @Mock AuthenticationDataProvider authDataProvider;
  @Mock DeviceDetailsProvider deviceDetailsProvider;
  @Mock Session session;
  @Mock BusinessObject<Crm> crmBusinessObject;
  @Mock Crm crm;
  @Mock BusinessObject<SdkAuthData> sdk;
  @Mock SdkAuthData sdkAuthData;
  @Mock InteractorError interactorError;
  @Mock BusinessError businessError;
  @Mock BusinessObject<ClientAuthData> clietAuth;
  @Mock ClientAuthData clietAuthData;

  AuthenticationServiceImpl authenticationService;

  @Before public void setUp(){
    authenticationService = new AuthenticationServiceImpl(authDataProvider, deviceDetailsProvider, session);
    mockshareArrangements();
  }

  private void mockshareArrangements() {
    when(session.getApiKey()).thenReturn("API_KEY");
    when(session.getApiSecret()).thenReturn("API_SECRET");

    when(authDataProvider.authenticateSdk(any(Credentials.class))).thenReturn(sdk);
    when(authDataProvider.authenticateUser(any(Credentials.class), anyString())).thenReturn(
        clietAuth);
  }

  @Test public void shouldAuthenticateSuccess() throws Exception {



    arrangeGetCrmID();
    when(crmBusinessObject.isSuccess()).thenReturn(true);
    when(crmBusinessObject.getData()).thenReturn(crm);
    when(crm.getCrmId()).thenReturn("ID");

    mockshareArrangements();
    arrangeAuthSuccess();

    InteractorResponse interactorResponse = authenticationService.authenticate();

    verify(session).setTokenString(anyString());

    assertThat(interactorResponse, notNullValue());
    assertThat(interactorResponse.getResult(), notNullValue());
    ClientAuthData clientAuthData = (ClientAuthData)interactorResponse.getResult();
    assertThat(clientAuthData, instanceOf(ClientAuthData.class));
  }

  private void arrangeGetCrmID() {
    when(authDataProvider.retrieveCrm()).thenReturn(crmBusinessObject);
    when(crmBusinessObject.getData()).thenReturn(crm);
  }

  @Test public void shouldAuthenticateWithCrmIDSuccess() throws Exception {

    mockshareArrangements();
    arrangeAuthSuccess();

    InteractorResponse interactorResponse = authenticationService.authenticateUserWithCrmId("ID");

    verify(session).setTokenString(anyString());

    assertThat(interactorResponse, notNullValue());
    assertThat(interactorResponse.getResult(), notNullValue());
    ClientAuthData clientAuthData = (ClientAuthData)interactorResponse.getResult();
    assertThat(clientAuthData, instanceOf(ClientAuthData.class));

  }

  private void arrangeAuthSuccess() {
    arrangeSuccessSdkAuth();
    arrangeSuccessClientAuth();
    arrangeDeviceDetailsProvider();
  }

  private void arrangeSuccessClientAuth() {
    when(clietAuth.isSuccess()).thenReturn(true);
    when(clietAuth.getData()).thenReturn(clietAuthData);
    when(clietAuthData.getValue()).thenReturn("TOKEN");
  }

  private void arrangeSuccessSdkAuth() {
    when(sdk.isSuccess()).thenReturn(true);
    when(sdk.getData()).thenReturn(sdkAuthData);
    when(sdkAuthData.getValue()).thenReturn("TOKEN");
  }

  private void arrangeDeviceDetailsProvider() {
    when(deviceDetailsProvider.getAndroidInstanceId()).thenReturn("getAndroidInstanceId");
    when(deviceDetailsProvider.getAndroidSecureId()).thenReturn("SecureId");
    when(deviceDetailsProvider.getAndroidSerialNumber()).thenReturn("SerialNumber");
    when(deviceDetailsProvider.getBluetoothMac()).thenReturn("BluetoothMac");
    when(deviceDetailsProvider.getWifiMac()).thenReturn("WifiMac");
  }

  @Test public void shouldNotAuthenticateWhenSDKAuthResponseIsKO() throws Exception {

    arrangeGetCrmID();

    when(sdk.isSuccess()).thenReturn(false);
    when(sdk.getBusinessError()).thenReturn(businessError);
    when(crmBusinessObject.getData()).thenReturn(crm);


    InteractorResponse interactorResponse = authenticationService.authenticate();

    verify(authDataProvider).retrieveCrm();
    verify(crmBusinessObject).isSuccess();

    assertThat(interactorResponse, notNullValue());
    assertThat(interactorResponse.getError(), notNullValue());
    assertThat(interactorResponse.getError(), instanceOf(SdkAuthError.class));
    BusinessError businessError = interactorResponse.getError().getError();
    assertThat(businessError, notNullValue());

  }

  @Test public void shouldNotAuthenticateWhenClientAuthResponseIsKO() throws Exception {

    arrangeGetCrmID();

    arrangeSuccessSdkAuth();
    arrangeDeviceDetailsProvider();

    when(authDataProvider.authenticateUser(any(Credentials.class), isNull(String.class))).thenReturn(
        clietAuth);

    when(clietAuth.isSuccess()).thenReturn(false);
    when(clietAuth.getBusinessError()).thenReturn(businessError);

    InteractorResponse interactorResponse = authenticationService.authenticate();

    verify(authDataProvider).retrieveCrm();

    assertThat(interactorResponse, notNullValue());
    assertThat(interactorResponse.getError(), notNullValue());
    assertThat(interactorResponse.getError(), instanceOf(AuthenticationError.class));
    BusinessError businessError = interactorResponse.getError().getError();
    assertThat(businessError, notNullValue());

  }

}