package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiDeviceCustomFields;

public class DeviceCustomFieldsExternalClassToModelMapper
        implements ExternalClassToModelMapper<ApiDeviceCustomFields, DeviceCustomFields> {

    @Override
    public DeviceCustomFields externalClassToModel(ApiDeviceCustomFields apiDeviceCustomFields) {
        DeviceCustomFields deviceCustomFields = new DeviceCustomFields();

        deviceCustomFields.setTags(apiDeviceCustomFields.getTags());
        deviceCustomFields.setBusinessUnits(apiDeviceCustomFields.getBusinessUnits());

        return deviceCustomFields;
    }
}
