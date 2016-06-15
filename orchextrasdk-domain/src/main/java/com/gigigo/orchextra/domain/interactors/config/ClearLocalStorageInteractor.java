package com.gigigo.orchextra.domain.interactors.config;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.services.config.LocalStorageService;

public class ClearLocalStorageInteractor implements Interactor<InteractorResponse<Boolean>> {

    private final LocalStorageService localStorageService;

    public ClearLocalStorageInteractor(LocalStorageService localStorageService) {
        this.localStorageService = localStorageService;
    }

    @Override
    public InteractorResponse<Boolean> call() throws Exception {
        Boolean isRemovedLocalStorage = localStorageService.clearLocalStorage();
        return new InteractorResponse<>(isRemovedLocalStorage);
    }
}
