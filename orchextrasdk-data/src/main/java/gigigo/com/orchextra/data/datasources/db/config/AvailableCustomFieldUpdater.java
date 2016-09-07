package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.model.AvailableCustomFieldRealm;
import io.realm.Realm;
import io.realm.RealmList;

public class AvailableCustomFieldUpdater {

    private final Mapper<AvailableCustomField, AvailableCustomFieldRealm> availableCustomFieldRealmMapper;

    public AvailableCustomFieldUpdater(Mapper<AvailableCustomField, AvailableCustomFieldRealm> availableCustomFieldRealmMapper) {
        this.availableCustomFieldRealmMapper = availableCustomFieldRealmMapper;
    }

    public void saveAvailablCustomFields(Realm realm, List<AvailableCustomField> availableCustomFieldList) {
        realm.clear(AvailableCustomFieldRealm.class);

        if (availableCustomFieldList != null) {

            RealmList<AvailableCustomFieldRealm> availableCustomFieldRealmRealmList = new RealmList<>();

            for (AvailableCustomField availableCustomField : availableCustomFieldList) {
                AvailableCustomFieldRealm availableCustomFieldRealm = availableCustomFieldRealmMapper.modelToExternalClass(availableCustomField);
                availableCustomFieldRealmRealmList.add(availableCustomFieldRealm);
            }

            realm.copyToRealm(availableCustomFieldRealmRealmList).size();
        }
    }
}
