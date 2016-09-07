package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;

import java.util.List;

public interface CrmDeviceDataProvider {
    BusinessObject<List<String>> retrieveCrmDeviceTags();

    void saveCrmDeviceUserTags(List<String> deviceTags);
}
