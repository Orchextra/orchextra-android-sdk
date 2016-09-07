package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiAvailableCustomFieldType;

public class AvailableCustomFieldExternalClassToModelMapper
        implements ExternalClassToModelMapper<Map<String, ApiAvailableCustomFieldType>, List<AvailableCustomField>> {

    @Override
    public List<AvailableCustomField> externalClassToModel(Map<String, ApiAvailableCustomFieldType> apiAvailableCustomFields) {
        List<AvailableCustomField> availableCustomFieldList = new ArrayList<>();

        Set<String> keySet = apiAvailableCustomFields.keySet();
        for (String key : keySet) {
            AvailableCustomField availableCustomField = new AvailableCustomField();
            availableCustomField.setKey(key);
            availableCustomField.setLabel(apiAvailableCustomFields.get(key).getLabel());
            availableCustomField.setType(apiAvailableCustomFields.get(key).getType());
            availableCustomFieldList.add(availableCustomField);
        }

        return availableCustomFieldList;
    }
}
