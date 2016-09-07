package gigigo.com.orchextra.data.datasources.db.config;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.DeviceBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.DeviceTagRealm;
import io.realm.Realm;

public class DeviceCustomFieldsReader {

    public List<String> readDeviceTags(Realm realm) {
        List<String> deviceTagList = new ArrayList<>();

        List<DeviceTagRealm> deviceTagRealmList = realm.where(DeviceTagRealm.class).findAll();

        for (DeviceTagRealm deviceTagRealm : deviceTagRealmList) {
            deviceTagList.add(deviceTagRealm.getTag());
        }

        return deviceTagList;
    }

    public List<String> readBusinessUnits(Realm realm) {
        List<String> deviceBusinessUnitList = new ArrayList<>();

        List<DeviceBusinessUnitRealm> deviceBusinessUnitRealmList = realm.where(DeviceBusinessUnitRealm.class).findAll();

        for (DeviceBusinessUnitRealm deviceBusinessUnitRealm : deviceBusinessUnitRealmList) {
            deviceBusinessUnitList.add(deviceBusinessUnitRealm.getBusinessUnit());
        }

        return deviceBusinessUnitList;
    }
}
