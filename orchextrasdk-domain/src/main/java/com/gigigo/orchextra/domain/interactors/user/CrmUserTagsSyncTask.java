package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;
import com.gigigo.orchextra.domain.services.user.CrmUserService;

import java.util.ArrayList;
import java.util.List;

public class CrmUserTagsSyncTask {

    private final CrmUserService crmUserService;

    public CrmUserTagsSyncTask(CrmUserService crmUserService)  {
        this.crmUserService = crmUserService;


    }

    public List<String> retrieveCrmUserTags() {
        BusinessObject<List<String>> boCrmUserTags = crmUserService.retrieveCrmUserTags();

        if (boCrmUserTags.isSuccess()) {
            return boCrmUserTags.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveUserTags(List<String> userTagList) {
        crmUserService.saveCrmUserTags(userTagList);
    }

    public List<String> retrieveCrmUserBusinessUnits() {
        BusinessObject<List<String>> boCrmUserBusinessUnits = crmUserService.retrieveCrmUserBusinessUnits();

        if (boCrmUserBusinessUnits.isSuccess()) {
            return boCrmUserBusinessUnits.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveUserBusinessUnits(List<String> userBusinessUnits) {
        crmUserService.saveUserBusinessUnits(userBusinessUnits);
    }

    public List<CustomField> retrieveCrmUserCustomFields() {
        BusinessObject<List<CustomField>> boCrmUserCustomFields = crmUserService.retrieveCrmUserCustomFields();

        if (boCrmUserCustomFields.isSuccess()) {
            return boCrmUserCustomFields.getData();
        } else {
            return new ArrayList<>();
        }
    }

    public void saveUserCustomFields(List<CustomField> customFieldList) {
        crmUserService.saveUserCustomFields(customFieldList);
    }
}
