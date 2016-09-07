package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

public class DeviceTagRealm  extends RealmObject {

    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
