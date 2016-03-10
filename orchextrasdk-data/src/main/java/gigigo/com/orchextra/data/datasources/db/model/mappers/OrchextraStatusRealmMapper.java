package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import gigigo.com.orchextra.data.datasources.db.model.OrchextraStatusRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraStatusRealmMapper implements Mapper<OrchextraStatus, OrchextraStatusRealm> {

  @Override public OrchextraStatus externalClassToModel(OrchextraStatusRealm data) {
    OrchextraStatus orchextraStatus = new OrchextraStatus();
    orchextraStatus.setInitialized(data.isInitialized());
    orchextraStatus.setStarted(data.isStarted());
    return orchextraStatus;
  }

  @Override public OrchextraStatusRealm modelToExternalClass(OrchextraStatus orchextraStatus) {

    OrchextraStatusRealm orchextraStatusRealm = new OrchextraStatusRealm();
    orchextraStatusRealm.setInitialized(orchextraStatus.isInitialized());
    orchextraStatusRealm.setStarted(orchextraStatus.isStarted());
    return orchextraStatusRealm;
  }

}
