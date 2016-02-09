package com.gigigo.orchetra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;

import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationDataProviderImplTest {

    @Mock
    AuthenticationDataSource authenticationDataSource;

    @Mock SessionDBDataSource sessionDBDataSource;

    @Mock BusinessObject<ClientAuthData> sessionToken;

    @Mock BusinessObject<Crm> businessObjectCrm;

    @Mock Crm crm;

    @Mock
    ClientAuthData clientAuthData;

    @Mock Credentials credentials;

    private AuthenticationDataProviderImpl authenticationDataProvider;

    @Before
    public void setUp() throws Exception {
        authenticationDataProvider = new AuthenticationDataProviderImpl(authenticationDataSource, sessionDBDataSource);
    }

    @Test
    public void shouldAuthenticateUserAndSaveCrmWhenCriteriaAreCorrectAndCrmIdIsNotEmpty() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);
        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crm);

        when(sessionToken.isSuccess()).thenReturn(false);
        when(sessionToken.getData()).thenReturn(clientAuthData);
        when(sessionToken.getData().isExpired()).thenReturn(false);

        when(authenticationDataSource.authenticateUser(any(Credentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(true);

        authenticationDataProvider.authenticateUser(credentials, "111111");

        verify(sessionDBDataSource).saveClientAuthResponse(sessionToken.getData());
        verify(sessionDBDataSource).saveUser(crm);
    }

    @Test
    public void shouldAuthenticateUserAndSaveWhenCriteriaAreCorrect() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);
        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crm);

        when(sessionToken.isSuccess()).thenReturn(false);
        when(sessionToken.getData()).thenReturn(clientAuthData);
        when(sessionToken.getData().isExpired()).thenReturn(false);
        when(crm.getCrmId()).thenReturn("111");


        when(authenticationDataSource.authenticateUser(any(Credentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(true);

        authenticationDataProvider.authenticateUser(credentials, null);

        verify(sessionDBDataSource).saveClientAuthResponse(any(ClientAuthData.class));
        verify(sessionDBDataSource).saveUser(crm);
    }

    @Test
    public void shouldReturnSessionTokenWhenAuthenticateIsFailed() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);
        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crm);

        when(sessionToken.isSuccess()).thenReturn(false);
        when(sessionToken.getData()).thenReturn(clientAuthData);
        when(sessionToken.getData().isExpired()).thenReturn(false);
        when(crm.getCrmId()).thenReturn("111");

        when(authenticationDataSource.authenticateUser(any(Credentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(false);

        authenticationDataProvider.authenticateUser(credentials, null);

        verify(authenticationDataSource).authenticateUser(credentials);
        verify(sessionDBDataSource, never()).saveClientAuthResponse(sessionToken.getData());
        verify(sessionDBDataSource, never()).saveUser(crm);
    }
}