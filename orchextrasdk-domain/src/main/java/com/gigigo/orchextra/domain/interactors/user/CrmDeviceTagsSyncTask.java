package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.services.user.CrmDeviceUserService;

import java.util.ArrayList;
import java.util.List;

public class CrmDeviceTagsSyncTask {

    private final CrmDeviceUserService crmDeviceUserService;

    public CrmDeviceTagsSyncTask(CrmDeviceUserService crmDeviceUserService)  {
        this.crmDeviceUserService = crmDeviceUserService;
    }

    public List<String> retrieveCrmUserTags() {
        BusinessObject<List<String>> boCrmDeviceTags = crmDeviceUserService.retrieveCrmDeviceTags();

        if (boCrmDeviceTags.isSuccess()) {
            return boCrmDeviceTags.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveDeviceTags(List<String> deviceTags) {
        crmDeviceUserService.saveCrmDeviceUserTags(deviceTags);
    }

    public List<String> retrieveDeviceBusinessUnits() {
        BusinessObject<List<String>> boDeviceBusinessUnits = crmDeviceUserService.retrieveCrmDeviceBusinessUnits();

        if (boDeviceBusinessUnits.isSuccess()) {
            return boDeviceBusinessUnits.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveDeviceBusinessUnits(List<String> deviceBusinessUnits) {
        crmDeviceUserService.saveCrmDeviceBusinessUnits(deviceBusinessUnits);
    }
}
