package com.gigigo.orchextra.domain.services.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.CrmDeviceDataProvider;

import java.util.List;

public class CrmDeviceUserService {

    private final CrmDeviceDataProvider crmDeviceDataProvider;

    public CrmDeviceUserService(CrmDeviceDataProvider crmDeviceDataProvider) {
        this.crmDeviceDataProvider = crmDeviceDataProvider;
    }

    public BusinessObject<List<String>> retrieveCrmDeviceTags() {
        return crmDeviceDataProvider.retrieveCrmDeviceTags();
    }

    public void saveCrmDeviceUserTags(List<String> deviceTags) {
        crmDeviceDataProvider.saveCrmDeviceUserTags(deviceTags);
    }
}
