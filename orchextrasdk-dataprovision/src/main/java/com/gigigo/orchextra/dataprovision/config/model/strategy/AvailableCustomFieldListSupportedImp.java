package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;
import java.util.List;

public class AvailableCustomFieldListSupportedImp implements AvailableCustomFieldListSupported {

  private final List<AvailableCustomField> availableCustomFieldTypeList;

  public AvailableCustomFieldListSupportedImp(
      List<AvailableCustomField> availableCustomFieldTypeList) {
    this.availableCustomFieldTypeList = availableCustomFieldTypeList;
  }

  @Override public List<AvailableCustomField> getAvailableCustomFieldList() {
    return availableCustomFieldTypeList;
  }

  @Override public boolean isSupported() {
    return availableCustomFieldTypeList != null;
  }
}
