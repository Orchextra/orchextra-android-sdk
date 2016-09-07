package com.gigigo.orchextra.domain.model.entities.tags;

import java.util.List;

public class CrmCustomFields {

    private List<String> tags;
    private List<String> businessUnits;
    private List<CustomField> customFieldList;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getBusinessUnits() {
        return businessUnits;
    }

    public void setBusinessUnits(List<String> businessUnits) {
        this.businessUnits = businessUnits;
    }

    public List<CustomField> getCustomFieldList() {
        return customFieldList;
    }

    public void setCustomFieldList(List<CustomField> customFieldList) {
        this.customFieldList = customFieldList;
    }
}
