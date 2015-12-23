package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SessionRealm extends RealmObject{

  private SdkAuthRealm sdkAuthRealm;
  private ClientAuthRealm clientAuthRealm;
  private CrmRealm crmRealm;
  private SdkAuthCredentialsRealm sdkAuthCredentialsRealm;
  private ClientAuthCredentialsRealm clientAuthCredentialsRealm;

  public SdkAuthRealm getSdkAuthRealm() {
    return sdkAuthRealm;
  }

  public void setSdkAuthRealm(SdkAuthRealm sdkAuthRealm) {
    this.sdkAuthRealm = sdkAuthRealm;
  }

  public ClientAuthRealm getClientAuthRealm() {
    return clientAuthRealm;
  }

  public void setClientAuthRealm(ClientAuthRealm clientAuthRealm) {
    this.clientAuthRealm = clientAuthRealm;
  }

  public CrmRealm getCrmRealm() {
    return crmRealm;
  }

  public void setCrmRealm(CrmRealm crmRealm) {
    this.crmRealm = crmRealm;
  }

  public SdkAuthCredentialsRealm getSdkAuthCredentialsRealm() {
    return sdkAuthCredentialsRealm;
  }

  public void setSdkAuthCredentialsRealm(SdkAuthCredentialsRealm sdkAuthCredentialsRealm) {
    this.sdkAuthCredentialsRealm = sdkAuthCredentialsRealm;
  }

  public ClientAuthCredentialsRealm getClientAuthCredentialsRealm() {
    return clientAuthCredentialsRealm;
  }

  public void setClientAuthCredentialsRealm(ClientAuthCredentialsRealm clientAuthCredentialsRealm) {
    this.clientAuthCredentialsRealm = clientAuthCredentialsRealm;
  }
}
