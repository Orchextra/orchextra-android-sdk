package gigigo.com.orchextra.data.datasources.api.model.responses;

import java.util.List;

public class ApiDeviceCustomFields {

    private List<String> tags;
    private List<String> businessUnits;

    public List<String> getTags() {
        return tags;
    }

    public List<String> getBusinessUnits() {
        return businessUnits;
    }
}
