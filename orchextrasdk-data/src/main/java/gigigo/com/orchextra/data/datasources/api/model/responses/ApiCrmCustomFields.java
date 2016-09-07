package gigigo.com.orchextra.data.datasources.api.model.responses;

import java.util.List;
import java.util.Map;

public class ApiCrmCustomFields {

    private List<String> tags;
    private List<String> businessUnits;
    private Map<String, String> customFields;

    public List<String> getTags() {
        return tags;
    }

    public List<String> getBusinessUnits() {
        return businessUnits;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }
}
