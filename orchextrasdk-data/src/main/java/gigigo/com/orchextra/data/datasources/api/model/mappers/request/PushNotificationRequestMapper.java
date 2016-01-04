package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.domain.entities.NotificationPush;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiNotificationPush;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class PushNotificationRequestMapper implements RequestMapper<NotificationPush, ApiNotificationPush>{

  @Override public ApiNotificationPush modelToData(NotificationPush notificationPush) {

    ApiNotificationPush apiNotificationPush = new ApiNotificationPush();
    apiNotificationPush.setSenderId(notificationPush.getSenderId());
    apiNotificationPush.setToken(notificationPush.getToken());

    return apiNotificationPush;
  }
}
