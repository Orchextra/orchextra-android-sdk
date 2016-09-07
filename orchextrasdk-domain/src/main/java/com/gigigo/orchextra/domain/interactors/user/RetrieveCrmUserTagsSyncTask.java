package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.services.user.CrmUserService;

import java.util.List;

public class RetrieveCrmUserTagsSyncTask {

    private final CrmUserService crmUserService;

    public RetrieveCrmUserTagsSyncTask(CrmUserService crmUserService)  {
        this.crmUserService = crmUserService;
    }

    public List<String> retrieveCrmUserTags() {
        BusinessObject<List<String>> boCrmUserTags = crmUserService.retrieveCrmUserTags();

        if (boCrmUserTags.isSuccess()) {
            return boCrmUserTags.getData();
        } else {
            return null;
        }
    }
}
