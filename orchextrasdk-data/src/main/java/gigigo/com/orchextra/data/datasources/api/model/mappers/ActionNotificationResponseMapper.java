package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiNotification;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 19/12/15.
 */
public class ActionNotificationResponseMapper implements ResponseMapper<Notification, ApiNotification>{

  @Override public Notification dataToModel(ApiNotification apiNotification) {
    Notification notification = new Notification();
    notification.setTitle(apiNotification.getTitle());
    notification.setBody(apiNotification.getBody());
    return notification;
  }

}
