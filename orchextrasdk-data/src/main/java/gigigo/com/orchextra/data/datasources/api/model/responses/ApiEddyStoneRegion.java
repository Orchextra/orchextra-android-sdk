package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nubor on 17/07/2017.
 */

public class ApiEddyStoneRegion extends ApiProximityItem {

  //@Expose @SerializedName("name") private String name;

  @Expose @SerializedName("namespace") private String namespace;

  @Expose @SerializedName("active") private boolean active;

  //public String getName() {
  //  return name;
  //}
  //
  //public void setName(String name) {
  //  this.name = name;
  //}

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public boolean getActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}