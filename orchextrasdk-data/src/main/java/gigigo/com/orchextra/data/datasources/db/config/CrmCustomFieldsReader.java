package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.CrmBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmCustomFieldRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmTagRealm;
import io.realm.Realm;

public class CrmCustomFieldsReader {

    public List<String> readCrmTags(Realm realm) {
        List<String> crmTagList = new ArrayList<>();

        List<CrmTagRealm> crmTagRealmList = realm.where(CrmTagRealm.class).findAll();

        for (CrmTagRealm crmTagRealm : crmTagRealmList) {
            crmTagList.add(crmTagRealm.getTag());
        }

        return crmTagList;
    }

    public List<String> readBusinessUnits(Realm realm) {
        List<String> crmBusinessUnitList = new ArrayList<>();

        List<CrmBusinessUnitRealm> crmBusinessUnitRealmList = realm.where(CrmBusinessUnitRealm.class).findAll();

        for (CrmBusinessUnitRealm crmBusinessUnitRealm : crmBusinessUnitRealmList) {
            crmBusinessUnitList.add(crmBusinessUnitRealm.getBusinessUnit());
        }

        return crmBusinessUnitList;
    }

    public List<CustomField> readCustomFields(Realm realm) {
        List<CustomField> customFieldList = new ArrayList<>();

        List<CrmCustomFieldRealm> crmCustomFieldRealmList = realm.where(CrmCustomFieldRealm.class).findAll();

        for (CrmCustomFieldRealm crmCustomFieldRealm : crmCustomFieldRealmList) {
            CustomField customField = new CustomField();
            customField.setKey(crmCustomFieldRealm.getKey());
            customField.setValue(crmCustomFieldRealm.getValue());
            customFieldList.add(customField);
        }

        return customFieldList;
    }
}
