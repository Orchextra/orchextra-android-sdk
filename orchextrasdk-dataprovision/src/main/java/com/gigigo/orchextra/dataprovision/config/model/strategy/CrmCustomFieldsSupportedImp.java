package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;

public class CrmCustomFieldsSupportedImp implements CrmCustomFieldsSupported {

  private final CrmCustomFields crmCustomFields;

  public CrmCustomFieldsSupportedImp(CrmCustomFields crmCustomFields) {
    this.crmCustomFields = crmCustomFields;
  }

  @Override public CrmCustomFields getCrmCustomFields() {
    return crmCustomFields;
  }

  @Override public boolean isSupported() {
    return crmCustomFields != null;
  }
}
