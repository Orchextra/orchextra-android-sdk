package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.services.user.CrmDeviceUserService;

import java.util.List;

public class SaveCrmDeviceTagsInteractor implements Interactor<InteractorResponse<Object>> {

    private final CrmDeviceUserService crmDeviceUserService;

    private List<String> deviceTags;

    public SaveCrmDeviceTagsInteractor(CrmDeviceUserService crmDeviceUserService) {
        this.crmDeviceUserService = crmDeviceUserService;
    }

    @Override
    public InteractorResponse<Object> call() throws Exception {
        crmDeviceUserService.saveCrmDeviceUserTags(deviceTags);
        return new InteractorResponse<>(null);
    }

    public void setCrmDeviceTags(List<String> deviceTags) {
        this.deviceTags = deviceTags;
    }
}
