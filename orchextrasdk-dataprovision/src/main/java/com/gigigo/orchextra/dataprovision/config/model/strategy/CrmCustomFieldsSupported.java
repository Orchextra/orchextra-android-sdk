package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.MethodSupported;
import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;

public interface CrmCustomFieldsSupported extends MethodSupported{
    CrmCustomFields getCrmCustomFields();
}
