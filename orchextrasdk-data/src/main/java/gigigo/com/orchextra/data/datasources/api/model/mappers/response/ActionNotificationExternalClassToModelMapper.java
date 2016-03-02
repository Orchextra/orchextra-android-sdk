package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiNotification;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionNotificationExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiNotification, Notification> {

  @Override public Notification externalClassToModel(ApiNotification apiNotification) {
    Notification notification = new Notification();
    notification.setTitle(apiNotification.getTitle());
    notification.setBody(apiNotification.getBody());
    return notification;
  }
}
