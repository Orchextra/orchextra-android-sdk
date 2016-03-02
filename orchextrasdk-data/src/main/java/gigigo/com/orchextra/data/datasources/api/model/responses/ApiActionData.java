package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiActionData {

  @Expose @SerializedName("trackId") private String trackId;

  @Expose @SerializedName("id") private String id;

  @Expose @SerializedName("type") private String type;

  @Expose @SerializedName("url") private String url;

  @Expose @SerializedName("notification") private ApiNotification notification;

  @Expose @SerializedName("schedule") private ApiSchedule schedule;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public ApiNotification getNotification() {
    return notification;
  }

  public void setNotification(ApiNotification notification) {
    this.notification = notification;
  }

  public ApiSchedule getSchedule() {
    return schedule;
  }

  public void ApiSchedule(ApiSchedule schedule) {
    this.schedule = schedule;
  }

  public String getTrackId() {
    return trackId;
  }

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
