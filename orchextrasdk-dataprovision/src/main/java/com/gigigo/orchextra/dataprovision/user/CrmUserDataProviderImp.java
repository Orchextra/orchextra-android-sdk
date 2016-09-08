package com.gigigo.orchextra.dataprovision.user;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.CrmUserDataProvider;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.List;

public class CrmUserDataProviderImp implements CrmUserDataProvider {

    private final TriggersConfigurationDBDataSource triggersConfigurationDBDataSource;

    public CrmUserDataProviderImp(TriggersConfigurationDBDataSource triggersConfigurationDBDataSource) {
        this.triggersConfigurationDBDataSource = triggersConfigurationDBDataSource;
    }

    @Override
    public BusinessObject<List<String>> retrieveCrmUserTags() {
        return triggersConfigurationDBDataSource.retrieveCrmUserTags();
    }

    @Override
    public void saveCrmUserTags(List<String> userTagList) {
        triggersConfigurationDBDataSource.saveCrmUserTags(userTagList);
    }

    @Override
    public BusinessObject<List<String>> retrieveCrmUserBusinessUnits() {
        return triggersConfigurationDBDataSource.retrieveCrmUserBusinessUnits();
    }

    @Override
    public void saveUserBusinessUnits(List<String> userBusinessUnits) {
        triggersConfigurationDBDataSource.saveUserBusinessUnits(userBusinessUnits);
    }

    @Override
    public BusinessObject<List<CustomField>> retrieveCrmUserCustomFields() {
        return triggersConfigurationDBDataSource.retrieveCrmUserCustomFields();
    }

    @Override
    public void saveUserCustomFields(List<CustomField> customFieldList) {
        triggersConfigurationDBDataSource.saveUserCustomFields(customFieldList);
    }

}
