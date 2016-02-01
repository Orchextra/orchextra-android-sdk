package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.Credentials;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationInteractorTest {

    @Mock AuthenticationDataProvider authenticationDataProvider;

    @Mock
    BusinessError businessError;

    @Mock
    BusinessObject<SdkAuthData> sdk;

    @Mock SdkAuthData sdkAuthData;

    @Mock
    BusinessObject<ClientAuthData> user;

    @Mock
    DeviceDetailsProvider deviceDetailsProvider;

    private AuthenticationInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new AuthenticationInteractor(authenticationDataProvider, deviceDetailsProvider);
        interactor.setSdkAuthCredentials(new SdkAuthCredentials("Admin", "1234"));
    }

    @Test
    public void testCallNotSdkSuccess() throws Exception {
        when(authenticationDataProvider.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(false);

        interactor.call();

        verify(sdk).getBusinessError();
    }

    @Test
    public void testCallNotUserSuccess() throws Exception {
        when(authenticationDataProvider.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(true);

        when(sdk.getData()).thenReturn(sdkAuthData);

        when(authenticationDataProvider.authenticateUser(isA(Credentials.class), anyString())).thenReturn(user);

        when(user.isSuccess()).thenReturn(false);

        interactor.setCrm(anyString());
        interactor.call();

        verify(user).getBusinessError();
    }

    @Test
    public void testCallSuccess() throws Exception {
        when(authenticationDataProvider.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(true);

        when(sdk.getData()).thenReturn(sdkAuthData);

        when(authenticationDataProvider.authenticateUser(isA(Credentials.class), anyString())).thenReturn(user);

        when(user.isSuccess()).thenReturn(true);

        interactor.setCrm(anyString());
        interactor.call();

        verify(user).getData();
    }
}
