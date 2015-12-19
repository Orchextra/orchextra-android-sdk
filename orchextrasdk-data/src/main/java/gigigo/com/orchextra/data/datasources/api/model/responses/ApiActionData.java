package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.types.CustomAction;
import com.gigigo.orchextra.domain.entities.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.entities.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.entities.actions.types.ScanAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import com.gigigo.orchextra.domain.entities.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.entities.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.entities.actions.types.WebViewAction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 14/12/15.
 */
public class ApiActionData {

  @Expose @SerializedName("type")
  private String type;

  @Expose @SerializedName("url")
  private String url;

  @Expose @SerializedName("notification")
  private ApiNotification notification;

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

  public static class ActionBuilder {

    private ActionType actionType;
    private String url;
    private Notification notification;

    public ActionBuilder(ActionType actionType, String url, Notification notification) {
      this.actionType = actionType;
      this.url = url;
      this.notification = notification;
    }

    public BasicAction build() {
      switch (actionType){
        case BROWSER:
          return new BrowserAction(url, notification);
        case WEBVIEW:
          return new WebViewAction(url, notification);
        case SCAN:
          return new ScanAction(url, notification);
        case VUFORIA_SCAN:
          return new VuforiaScanAction(url, notification);
        case CUSTOM_SCHEME:
          return new CustomAction(url, notification);
        case NOTIFICATION:
          return new NotificationAction(url, notification);
        case NOT_DEFINED:
        default:
          return new EmptyAction(url, notification);
      }
    }
  }
}
