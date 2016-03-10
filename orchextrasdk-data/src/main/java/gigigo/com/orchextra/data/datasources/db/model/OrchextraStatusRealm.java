package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/3/16.
 */
public class OrchextraStatusRealm extends RealmObject {

  @PrimaryKey private int id;
  private boolean initialized;
  private boolean started;

  public void setInitialized(boolean initialized) {
    this.initialized = initialized;
  }

  public void setStarted(boolean started) {
    this.started = started;
  }

  public boolean isInitialized() {
    return initialized;
  }

  public boolean isStarted() {
    return started;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
