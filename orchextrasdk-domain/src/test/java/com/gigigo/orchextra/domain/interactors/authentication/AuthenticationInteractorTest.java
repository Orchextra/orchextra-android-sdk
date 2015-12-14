package com.gigigo.orchextra.domain.interactors.authentication;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Sdk;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.entities.User;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationInteractorTest {

    @Mock
    AuthenticationRepository authenticationRepository;

    @Mock
    BusinessError businessError;

    @Mock
    BusinessObject<Sdk> sdk;

    @Mock
    BusinessObject<User> user;

    private AuthenticationInteractor interactor;

    @Before
    public void setUp() throws Exception {
        interactor = new AuthenticationInteractor(authenticationRepository);
        interactor.setSdkAuthCredentials(new SdkAuthCredentials("Admin", "1234"));
    }

    @Test
    public void testCallNotSdkSuccess() throws Exception {
        when(authenticationRepository.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(false);

        interactor.call();

        verify(sdk).getBusinessError();
    }

    @Test
    public void testCallNotUserSuccess() throws Exception {
        when(authenticationRepository.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(true);

        when(authenticationRepository.authenticateUser(sdk.getData())).thenReturn(user);

        when(user.isSuccess()).thenReturn(false);

        interactor.call();

        verify(user).getBusinessError();
    }

    @Test
    public void testCallSuccess() throws Exception {
        when(authenticationRepository.authenticateSdk(isA(SdkAuthCredentials.class))).thenReturn(sdk);

        when(sdk.isSuccess()).thenReturn(true);

        when(authenticationRepository.authenticateUser(sdk.getData())).thenReturn(user);

        when(user.isSuccess()).thenReturn(true);

        interactor.call();

        verify(user).getData();
    }
}
