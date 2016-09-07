package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.services.user.CrmUserService;

import java.util.List;

public class SaveCrmUserTagsInteractor implements Interactor<InteractorResponse<Object>> {

    private final CrmUserService crmUserService;

    private List<String> userTagList;

    public SaveCrmUserTagsInteractor(CrmUserService crmUserService) {
        this.crmUserService = crmUserService;
    }

    @Override
    public InteractorResponse<Object> call() throws Exception {
        crmUserService.saveCrmUserTags(userTagList);
        return new InteractorResponse<>(null);
    }

    public void setCrmUserTags(List<String> userTagList) {
        this.userTagList = userTagList;
    }
}
