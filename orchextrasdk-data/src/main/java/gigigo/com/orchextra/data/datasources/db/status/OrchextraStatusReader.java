package gigigo.com.orchextra.data.datasources.db.status;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraStatusReader {

  private final ExternalClassToModelMapper<OrchextraStatusRealm, OrchextraStatus> statusRealmMapper;

  public OrchextraStatusReader(ExternalClassToModelMapper<OrchextraStatusRealm, OrchextraStatus> statusRealmMapper) {
    this.statusRealmMapper = statusRealmMapper;
  }

  public OrchextraStatus readStatus(Realm realm) {

    RealmResults<OrchextraStatusRealm> orchextraStatusRealms = realm.where(OrchextraStatusRealm.class).findAll();
    if (orchextraStatusRealms.size() > 0) {
      GGGLogImpl.log("OrchextraStatus found");
      return statusRealmMapper.externalClassToModel(orchextraStatusRealms.first());
    } else {
      GGGLogImpl.log("OrchextraStatus not found");
      throw new NotFountRealmObjectException("OrchextraStatusRealm object not found");
    }
  }
}
