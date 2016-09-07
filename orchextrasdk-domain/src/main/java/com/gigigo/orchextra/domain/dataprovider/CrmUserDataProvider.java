package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;

import java.util.List;

public interface CrmUserDataProvider {
    BusinessObject<List<String>> retrieveCrmUserTags();

    void saveCrmUserTags(List<String> userTagList);
}
