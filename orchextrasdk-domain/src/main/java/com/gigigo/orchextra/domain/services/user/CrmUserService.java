package com.gigigo.orchextra.domain.services.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.CrmUserDataProvider;

import java.util.List;

public class CrmUserService {

    private final CrmUserDataProvider crmUserDataProvider;

    public CrmUserService(CrmUserDataProvider crmUserDataProvider) {
        this.crmUserDataProvider = crmUserDataProvider;
    }

    public BusinessObject<List<String>> retrieveCrmUserTags() {
        return crmUserDataProvider.retrieveCrmUserTags();
    }

    public void saveCrmUserTags(List<String> userTagList) {
        crmUserDataProvider.saveCrmUserTags(userTagList);
    }
}
