package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigInfoResultRealm extends RealmObject {

  private int requestWaitTime;

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }
}
