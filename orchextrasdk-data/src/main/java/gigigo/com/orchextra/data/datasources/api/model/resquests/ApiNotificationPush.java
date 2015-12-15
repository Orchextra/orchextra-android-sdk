package gigigo.com.orchextra.data.datasources.api.model.resquests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class ApiNotificationPush {

  @Expose @SerializedName("token")
  private String token;

  @Expose @SerializedName("senderId")
  private String senderId;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }
}
