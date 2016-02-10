package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class BeaconEventRealm extends RealmObject{

  public static final String TIMESTAMPT_FIELD_NAME = "timeStampt";
  public static final String CODE_FIELD_NAME = "code";
  public static final String DISTANCE_FIELD_NAME = "beaconDistance";

  @Required
  private String code;
  private String uuid;
  private int mayor;
  private int minor;
  private String beaconDistance;
  private long timeStampt;

  public BeaconEventRealm() {
    this.timeStampt = System.currentTimeMillis();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public int getMayor() {
    return mayor;
  }

  public void setMayor(int mayor) {
    this.mayor = mayor;
  }

  public int getMinor() {
    return minor;
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public String getBeaconDistance() {
    return beaconDistance;
  }

  public void setBeaconDistance(String beaconDistance) {
    this.beaconDistance = beaconDistance;
  }

  public long getTimeStampt() {
    return timeStampt;
  }

  public void setTimeStampt(long timeStampt) {
    this.timeStampt = timeStampt;
  }
}
