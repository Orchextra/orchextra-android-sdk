package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiCrmCustomFields;

public class CrmCustomFieldsExternalClassToModelMapper
        implements ExternalClassToModelMapper<ApiCrmCustomFields, CrmCustomFields> {


    @Override
    public CrmCustomFields externalClassToModel(ApiCrmCustomFields apiCrmCustomFields) {
        CrmCustomFields crmCustomFields = new CrmCustomFields();

        crmCustomFields.setBusinessUnits(apiCrmCustomFields.getBusinessUnits());
        crmCustomFields.setTags(apiCrmCustomFields.getTags());

        if (apiCrmCustomFields.getCustomFields() != null) {
            List<CustomField> customFieldList = new ArrayList<>();
            for (String key : apiCrmCustomFields.getCustomFields().keySet()) {

                CustomField customField = new CustomField();
                customField.setKey(key);
                customField.setValue(apiCrmCustomFields.getCustomFields().get(key));

                customFieldList.add(customField);
            }
            crmCustomFields.setCustomFieldList(customFieldList);
        }

        return crmCustomFields;
    }
}
