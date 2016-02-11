package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ThemeRealm extends RealmObject {

  private String primaryColor;
  private String secondaryColor;

  public String getPrimaryColor() {
    return primaryColor;
  }

  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }

  public String getSecondaryColor() {
    return secondaryColor;
  }

  public void setSecondaryColor(String secondaryColor) {
    this.secondaryColor = secondaryColor;
  }
}
