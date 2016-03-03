/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class BeaconEventRealm extends RealmObject {

  public static final String TIMESTAMPT_FIELD_NAME = "timeStampt";
  public static final String CODE_FIELD_NAME = "code";
  public static final String DISTANCE_FIELD_NAME = "beaconDistance";

  @PrimaryKey private String keyForRealm;
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

  public void setKeyForRealm(String key) {
    this.keyForRealm = key;
  }

  public String getKeyForRealm() {
    return keyForRealm;
  }
}
