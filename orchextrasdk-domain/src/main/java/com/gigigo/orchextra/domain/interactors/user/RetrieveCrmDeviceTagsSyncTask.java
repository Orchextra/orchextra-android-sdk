package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.services.user.CrmDeviceUserService;

import java.util.List;

public class RetrieveCrmDeviceTagsSyncTask {

    private final CrmDeviceUserService crmDeviceUserService;

    public RetrieveCrmDeviceTagsSyncTask(CrmDeviceUserService crmDeviceUserService)  {
        this.crmDeviceUserService = crmDeviceUserService;
    }

    public List<String> retrieveCrmUserTags() {
        BusinessObject<List<String>> boCrmUserTags = crmDeviceUserService.retrieveCrmDeviceTags();

        if (boCrmUserTags.isSuccess()) {
            return boCrmUserTags.getData();
        } else {
            return null;
        }
    }
}
