package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class KeyWordRealm extends RealmObject {
  private String keyword;

  public KeyWordRealm() {
  }

  public KeyWordRealm(String keyword) {
    this.keyword = keyword;
  }

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }
}
