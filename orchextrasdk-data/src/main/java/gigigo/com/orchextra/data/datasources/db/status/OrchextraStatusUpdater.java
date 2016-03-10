package gigigo.com.orchextra.data.datasources.db.status;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraStatusUpdater {

  private final ModelToExternalClassMapper<OrchextraStatus, OrchextraStatusRealm> statusRealmMapper;

  public OrchextraStatusUpdater(
      ModelToExternalClassMapper<OrchextraStatus, OrchextraStatusRealm> statusRealmMapper) {
    this.statusRealmMapper = statusRealmMapper;

  }

  public OrchextraStatus saveStatus(Realm realm, OrchextraStatus orchextraStatus) {
    OrchextraStatusRealm realmItem = getRealmItem(realm, OrchextraStatusRealm.class);

    OrchextraStatusRealm orchextraStatusRealm = statusRealmMapper.modelToExternalClass(orchextraStatus);

    if (realmItem == null) {
      orchextraStatusRealm.setId(RealmDefaultInstance.getNextKey(realm, OrchextraStatusRealm.class));
    } else {
      orchextraStatusRealm.setId(realmItem.getId());
    }

    realm.copyToRealmOrUpdate(orchextraStatusRealm);

    return orchextraStatus;
  }

  private <T extends RealmObject> T getRealmItem(Realm realm, Class<T> classType) {
    return realm.where(classType).findFirst();
  }
}
