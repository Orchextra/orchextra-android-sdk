package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiDevice {

  @Expose @SerializedName("handset")
  private String handset;

  @Expose @SerializedName("language")
  private String language;

  @Expose @SerializedName("osVersion")
  private String osVersion;

  @Expose @SerializedName("os")
  private String os;

  @Expose @SerializedName("timeZone")
  private String timeZone;

  @Expose @SerializedName("advertiserId")
  private String advertiserId;

  @Expose @SerializedName("vendorId")
  private String vendorId;

  @Expose @SerializedName("instanceId")
  private String instanceId;

  @Expose @SerializedName("secureId")
  private String secureId;

  @Expose @SerializedName("serialNumber")
  private String serialNumber;

  @Expose @SerializedName("bluetoothMacAddress")
  private String bluetoothMacAddress;

  @Expose @SerializedName("wifiMacAddress")
  private String wifiMacAddress;

  public String getHandset() {
    return handset;
  }

  public void setHandset(String handset) {
    this.handset = handset;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getTimeZone() {
    return timeZone;
  }

  public void setTimeZone(String timeZone) {
    this.timeZone = timeZone;
  }

  public String getAdvertiserId() {
    return advertiserId;
  }

  public void setAdvertiserId(String advertiserId) {
    this.advertiserId = advertiserId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getSecureId() {
    return secureId;
  }

  public void setSecureId(String secureId) {
    this.secureId = secureId;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getBluetoothMacAddress() {
    return bluetoothMacAddress;
  }

  public void setBluetoothMacAddress(String bluetoothMacAddress) {
    this.bluetoothMacAddress = bluetoothMacAddress;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getWifiMacAddress() {
    return wifiMacAddress;
  }

  public void setWifiMacAddress(String wifiMacAddress) {
    this.wifiMacAddress = wifiMacAddress;
  }
}
