package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;
import com.gigigo.orchextra.domain.services.config.ConfigService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SaveUserInteractorTest {

    @Mock
    AuthenticationService authenticationService;

    @Mock
    ConfigService configService;

    @Mock
    InteractorResponse interactorResponse;

    private SaveUserInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new SaveUserInteractor(authenticationService, configService);
        interactor.setCrm("1111");
    }

    @Test
    public void shouldReturnClientAuthData() throws Exception {
        when(authenticationService.authenticateUserWithCrmId(anyString())).thenReturn(interactorResponse);
        when(interactorResponse.hasError()).thenReturn(false);
        when(configService.refreshConfig()).thenReturn(any(InteractorResponse.class));

        interactor.call();

        verify(authenticationService).authenticateUserWithCrmId(anyString());
        verify(interactorResponse).hasError();
        verify(configService).refreshConfig();
    }

    @Test
    public void shouldDontRefreshConfigWhenAutheticationFails() throws Exception {
        when(authenticationService.authenticateUserWithCrmId(anyString())).thenReturn(interactorResponse);
        when(interactorResponse.hasError()).thenReturn(true);

        interactor.call();

        verify(authenticationService).authenticateUserWithCrmId(anyString());
        verify(interactorResponse).hasError();
        verify(configService, never()).refreshConfig();
    }
}
