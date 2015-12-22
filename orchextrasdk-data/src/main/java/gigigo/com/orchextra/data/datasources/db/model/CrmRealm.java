package gigigo.com.orchextra.data.datasources.db.model;

import com.gigigo.orchextra.domain.entities.GenderType;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class CrmRealm extends RealmObject {

  private String crmId;
  private String gender;
  private String birthDate;
  private RealmList<KeyWordRealm> keywords;

  public String getCrmId() {
    return crmId;
  }

  public void setCrmId(String crmId) {
    this.crmId = crmId;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public RealmList<KeyWordRealm> getKeywords() {
    return keywords;
  }

  public void setKeywords(RealmList<KeyWordRealm> keywords) {
    this.keywords = keywords;
  }
}
