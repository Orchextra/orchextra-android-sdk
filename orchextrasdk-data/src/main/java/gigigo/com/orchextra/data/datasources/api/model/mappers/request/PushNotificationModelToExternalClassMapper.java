package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiNotificationPush;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class PushNotificationModelToExternalClassMapper
    implements ModelToExternalClassMapper<NotificationPush, ApiNotificationPush> {

  @Override public ApiNotificationPush modelToExternalClass(NotificationPush notificationPush) {

    ApiNotificationPush apiNotificationPush = new ApiNotificationPush();
    apiNotificationPush.setSenderId(notificationPush.getSenderId());
    apiNotificationPush.setToken(notificationPush.getToken());

    return apiNotificationPush;
  }
}
