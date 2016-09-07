package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

public class DeviceBusinessUnitRealm extends RealmObject {

    private String businessUnit;

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }
}
