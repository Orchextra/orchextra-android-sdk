package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

public class AvailableCustomFieldRealm extends RealmObject {

    private String key;
    private String type;
    private String label;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
