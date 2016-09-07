package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

public class CrmCustomFieldRealm extends RealmObject{

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
