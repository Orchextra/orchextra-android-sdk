package com.gigigo.orchextra.domain.model.entities.tags;

import java.util.List;

public class DeviceCustomFields {

    private List<String> tags;
    private List<String> businessUnits;

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
}
