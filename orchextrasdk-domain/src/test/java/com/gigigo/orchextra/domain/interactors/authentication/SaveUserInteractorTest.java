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

package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusManager;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigDomainService;

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
public class SaveUserInteractorTest {

    @Mock
    AuthenticationService authenticationService;

    @Mock
    ConfigDomainService configDomainService;

    @Mock
    OrchextraStatusManager orchextraStatusManager;

    @Mock
    BusinessObject<CrmUser> saveUserResponse;

    private SaveUserInteractor interactor;

    CrmUser crmUser;

    @Before
    public void setUp() throws Exception {
        interactor = new SaveUserInteractor(authenticationService, configDomainService, orchextraStatusManager);
        crmUser = new CrmUser();
        crmUser.setCrmId("1111");
        interactor.setCrmUser(crmUser);
    }

    @Test
    public void shouldRefreshConfigWhenOrchextraIsStatedAndUserIsSaved() throws Exception {
        interactor.setHasReloadConfig(true);

        when(authenticationService.saveUser(crmUser)).thenReturn(saveUserResponse);
        when(saveUserResponse.isSuccess()).thenReturn(true);
        when(orchextraStatusManager.isStarted()).thenReturn(true);
        when(configDomainService.refreshConfig()).thenReturn(any(InteractorResponse.class));

        interactor.call();

        verify(authenticationService).saveUser(crmUser);
        verify(saveUserResponse).isSuccess();
        verify(configDomainService).refreshConfig();
    }

    @Test
    public void shouldDoesntRefreshConfigWhenOrchextraIsntStarted() throws Exception {
        interactor.setHasReloadConfig(true);

        when(authenticationService.saveUser(crmUser)).thenReturn(saveUserResponse);
        when(saveUserResponse.isSuccess()).thenReturn(true);
        when(orchextraStatusManager.isStarted()).thenReturn(false);
        when(configDomainService.refreshConfig()).thenReturn(any(InteractorResponse.class));

        interactor.call();

        verify(authenticationService).saveUser(crmUser);
        verify(saveUserResponse).isSuccess();
        verify(configDomainService, never()).refreshConfig();
    }

    @Test
    public void shouldDoesntRefreshConfigWhenHasReloadConfigIsFalse() throws Exception {
        interactor.setHasReloadConfig(false);

        when(authenticationService.saveUser(crmUser)).thenReturn(saveUserResponse);
        when(saveUserResponse.isSuccess()).thenReturn(true);
        when(orchextraStatusManager.isStarted()).thenReturn(true);
        when(configDomainService.refreshConfig()).thenReturn(any(InteractorResponse.class));

        interactor.call();

        verify(authenticationService).saveUser(crmUser);
        verify(saveUserResponse).isSuccess();
        verify(configDomainService, never()).refreshConfig();
    }

    @Test
    public void shouldDontRefreshConfigWhenAutheticationFails() throws Exception {
        when(authenticationService.saveUser(crmUser)).thenReturn(saveUserResponse);
        when(saveUserResponse.isSuccess()).thenReturn(false);

        interactor.call();

        verify(authenticationService).saveUser(crmUser);
        verify(saveUserResponse).isSuccess();
        verify(configDomainService, never()).refreshConfig();
    }
}
