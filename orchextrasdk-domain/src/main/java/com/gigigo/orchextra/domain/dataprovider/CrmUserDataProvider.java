package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.List;

public interface CrmUserDataProvider {
    BusinessObject<List<String>> retrieveCrmUserTags();

    void saveCrmUserTags(List<String> userTagList);

    BusinessObject<List<String>> retrieveCrmUserBusinessUnits();

    void saveUserBusinessUnits(List<String> userBusinessUnits);

    BusinessObject<List<CustomField>> retrieveCrmUserCustomFields();

    void saveUserCustomFields(List<CustomField> customFieldList);
}
