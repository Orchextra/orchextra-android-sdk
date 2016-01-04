package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.entities.NotificationPush;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiNotificationPush;

import static org.junit.Assert.assertEquals;

public class PushNotificationRequestMapperTest {

    @Test
    public void testModelToDataOk() throws Exception {

        NotificationPush notificationPush = new NotificationPush();
        notificationPush.setSenderId("SenderId");
        notificationPush.setToken("TokenId");

        PushNotificationRequestMapper mapper = new PushNotificationRequestMapper();
        ApiNotificationPush apiNotificationPush = mapper.modelToData(notificationPush);

        assertEquals("SenderId", apiNotificationPush.getSenderId());
        assertEquals("TokenId", notificationPush.getToken());
    }
}