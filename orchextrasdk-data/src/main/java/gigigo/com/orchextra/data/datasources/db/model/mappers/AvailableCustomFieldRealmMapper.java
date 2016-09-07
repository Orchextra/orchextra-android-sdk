package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;

import gigigo.com.orchextra.data.datasources.db.model.AvailableCustomFieldRealm;

public class AvailableCustomFieldRealmMapper implements Mapper<AvailableCustomField, AvailableCustomFieldRealm> {

    @Override
    public AvailableCustomField externalClassToModel(AvailableCustomFieldRealm data) {
        AvailableCustomField model = new AvailableCustomField();

        model.setKey(data.getKey());
        model.setLabel(data.getLabel());
        model.setType(data.getType());

        return model;
    }

    @Override
    public AvailableCustomFieldRealm modelToExternalClass(AvailableCustomField model) {
        AvailableCustomFieldRealm data = new AvailableCustomFieldRealm();

        data.setKey(model.getKey());
        data.setLabel(model.getLabel());
        data.setType(data.getType());

        return data;
    }
}
