package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 30/11/15.
 */
public class OrchextraApiErrorResponse {

  @SerializedName("code")
  private int code;
  @SerializedName("message")
  private String message;

  public OrchextraApiErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
