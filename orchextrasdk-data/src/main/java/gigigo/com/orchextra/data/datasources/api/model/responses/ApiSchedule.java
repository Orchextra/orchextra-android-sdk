package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class ApiSchedule {

  @Expose @SerializedName("seconds")
  private int seconds;

  @Expose @SerializedName("cancelable")
  private boolean cancelable;

  public int getSeconds() {
    return seconds;
  }

  public void setSeconds(int seconds) {
    this.seconds = seconds;
  }

  public boolean isCancelable() {
    return cancelable;
  }

  public void setCancelable(boolean cancelable) {
    this.cancelable = cancelable;
  }
}
