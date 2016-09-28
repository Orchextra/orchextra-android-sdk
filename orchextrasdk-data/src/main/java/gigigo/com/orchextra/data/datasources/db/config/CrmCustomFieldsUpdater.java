package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.CrmBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmCustomFieldRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmTagRealm;
import io.realm.Realm;
import io.realm.RealmList;

public class CrmCustomFieldsUpdater {

    public void saveCrmCustomFields(Realm realm, CrmCustomFields crmCustomFields) {
        if (crmCustomFields != null) {

            copyCrmTagsToRealm(realm, crmCustomFields.getTags());
            copyCrmBusinessUnits(realm, crmCustomFields.getBusinessUnits());
            copyCrmCustomFieldList(realm, crmCustomFields.getCustomFieldList());
        }
    }

    private void copyCrmTagsToRealm(Realm realm, List<String> tags) {
        realm.delete(CrmTagRealm.class);
        if (tags != null) {
            RealmList<CrmTagRealm> realmTagList = new RealmList<>();
            for (String tag : tags) {
                CrmTagRealm realmString = new CrmTagRealm();
                realmString.setTag(tag);
                realmTagList.add(realmString);
            }
            realm.copyToRealm(realmTagList);
        }
    }

    private void copyCrmBusinessUnits(Realm realm, List<String> businessUnits) {
        realm.delete(CrmBusinessUnitRealm.class);
        if (businessUnits != null) {
            RealmList<CrmBusinessUnitRealm> realmBusinessUnitList = new RealmList<>();
            for (String tag : businessUnits) {
                CrmBusinessUnitRealm realmString = new CrmBusinessUnitRealm();
                realmString.setBusinessUnit(tag);
                realmBusinessUnitList.add(realmString);
            }
            realm.copyToRealm(realmBusinessUnitList);
        }
    }

    private void copyCrmCustomFieldList(Realm realm, List<CustomField> customFieldList) {
        realm.delete(CrmCustomFieldRealm.class);
        if (customFieldList != null) {
            RealmList<CrmCustomFieldRealm> crmCustomFieldRealmList = new RealmList<>();
            for (CustomField customField : customFieldList) {
                CrmCustomFieldRealm crmCustomFieldRealm = new CrmCustomFieldRealm();
                crmCustomFieldRealm.setKey(customField.getKey());
                crmCustomFieldRealm.setValue(customField.getValue());
                crmCustomFieldRealmList.add(crmCustomFieldRealm);
            }
            realm.copyToRealm(crmCustomFieldRealmList);
        }
    }
}
