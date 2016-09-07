package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.MethodSupported;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;

import java.util.List;

public interface AvailableCustomFieldListSupported extends MethodSupported {
    List<AvailableCustomField> getAvailableCustomFieldList();
}
