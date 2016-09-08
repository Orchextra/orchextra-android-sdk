package com.gigigo.orchextra.domain.services.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.CrmUserDataProvider;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

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

    public BusinessObject<List<String>> retrieveCrmUserBusinessUnits() {
        return crmUserDataProvider.retrieveCrmUserBusinessUnits();
    }

    public void saveUserBusinessUnits(List<String> userBusinessUnits) {
        crmUserDataProvider.saveUserBusinessUnits(userBusinessUnits);
    }

    public BusinessObject<List<CustomField>> retrieveCrmUserCustomFields() {
        return crmUserDataProvider.retrieveCrmUserCustomFields();
    }

    public void saveUserCustomFields(List<CustomField> customFieldList) {
        crmUserDataProvider.saveUserCustomFields(customFieldList);
    }
}
