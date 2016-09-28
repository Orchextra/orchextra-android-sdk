package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.DeviceBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.DeviceTagRealm;
import io.realm.Realm;
import io.realm.RealmList;

public class DeviceCustomFieldsUpdater {

    public void saveDeviceCustomFields(Realm realm, DeviceCustomFields deviceCustomFields) {
        if (deviceCustomFields != null) {

            copyDeviceTagsToRealm(realm, deviceCustomFields.getTags());
            copyDeviceBusinessUnits(realm, deviceCustomFields.getBusinessUnits());
        }
    }

    private void copyDeviceTagsToRealm(Realm realm, List<String> tags) {
        realm.delete(DeviceTagRealm.class);
        if (tags != null) {
            RealmList<DeviceTagRealm> realmTagList = new RealmList<>();
            for (String tag : tags) {
                DeviceTagRealm realmString = new DeviceTagRealm();
                realmString.setTag(tag);
                realmTagList.add(realmString);
            }
            realm.copyToRealm(realmTagList);
        }
    }

    private void copyDeviceBusinessUnits(Realm realm, List<String> businessUnits) {
        realm.delete(DeviceBusinessUnitRealm.class);
        if (businessUnits != null) {
            RealmList<DeviceBusinessUnitRealm> realmBusinessUnitList = new RealmList<>();
            for (String tag : businessUnits) {
                DeviceBusinessUnitRealm realmString = new DeviceBusinessUnitRealm();
                realmString.setBusinessUnit(tag);
                realmBusinessUnitList.add(realmString);
            }
            realm.copyToRealm(realmBusinessUnitList);
        }
    }
}
