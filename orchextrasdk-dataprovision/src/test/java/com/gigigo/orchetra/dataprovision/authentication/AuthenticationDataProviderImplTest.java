/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchetra.dataprovision.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;

import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;

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


    @Mock BusinessObject<CrmUser> businessObjectCrm;

    @Mock
    CrmUser crmUser;

    @Mock
    ClientAuthData clientAuthData;

    @Mock
    ClientAuthCredentials clientAuthCredentials;

    private AuthenticationDataProviderImpl authenticationDataProvider;

    @Before
    public void setUp() throws Exception {
        authenticationDataProvider = new AuthenticationDataProviderImpl(authenticationDataSource, sessionDBDataSource);
    }

    @Test
    public void shouldAuthenticateUserAndSaveCrmWhenCriteriaAreCorrectAndCrmIdIsNotEmpty() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(false);

        when(authenticationDataSource.authenticateUser(any(AuthCredentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(true);

        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crmUser);


        authenticationDataProvider.authenticateUser(clientAuthCredentials, "111111");

        verify(sessionDBDataSource).saveClientAuthResponse(sessionToken.getData());
        verify(sessionDBDataSource).saveUser(crmUser);
    }

    @Test
    public void shouldAuthenticateUserAndSaveWhenCriteriaAreCorrect() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);
        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crmUser);

        when(sessionToken.isSuccess()).thenReturn(false);

        when(authenticationDataSource.authenticateUser(any(AuthCredentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(true);

        authenticationDataProvider.authenticateUser(clientAuthCredentials, null);

        verify(sessionDBDataSource).saveClientAuthResponse(any(ClientAuthData.class));
    }

    @Test
    public void shouldReturnSessionTokenWhenAuthenticateIsFailed() throws Exception {
        when(sessionDBDataSource.getSessionToken()).thenReturn(sessionToken);
        when(sessionDBDataSource.getCrm()).thenReturn(businessObjectCrm);
        when(businessObjectCrm.getData()).thenReturn(crmUser);

        when(sessionToken.isSuccess()).thenReturn(false);
        when(sessionToken.getData()).thenReturn(clientAuthData);
        when(sessionToken.getData().isExpired()).thenReturn(false);
        when(crmUser.getCrmId()).thenReturn("111");

        when(authenticationDataSource.authenticateUser(any(AuthCredentials.class))).thenReturn(sessionToken);

        when(sessionToken.isSuccess()).thenReturn(false);

        authenticationDataProvider.authenticateUser(clientAuthCredentials, null);

        verify(authenticationDataSource).authenticateUser(clientAuthCredentials);
        verify(sessionDBDataSource, never()).saveClientAuthResponse(sessionToken.getData());
        verify(sessionDBDataSource, never()).saveUser(crmUser);
    }
}