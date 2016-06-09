package com.gigigo.orchextra.domain.services.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;

public class LocalStorageService {

    private final ConfigDataProvider configDataProvider;

    public LocalStorageService(ConfigDataProvider configDataProvider) {
        this.configDataProvider = configDataProvider;
    }

    public Boolean clearLocalStorage() {
        BusinessObject<Boolean> boRemovedLocalStorage = configDataProvider.removeLocalStorage();
        return boRemovedLocalStorage.isSuccess();
    }
}
