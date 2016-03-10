package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.orchextra.domain.model.vo.NotificationPush;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.requests.ApiNotificationPush;

import static org.junit.Assert.assertEquals;

public class PushNotificationRequestMapperTest {

    @Test
    public void testModelToDataOk() throws Exception {

        NotificationPush notificationPush = new NotificationPush();
        notificationPush.setSenderId("SenderId");
        notificationPush.setToken("TokenId");

        PushNotificationModelToExternalClassMapper
            mapper = new PushNotificationModelToExternalClassMapper();
        ApiNotificationPush apiNotificationPush = mapper.modelToExternalClass(notificationPush);

        assertEquals("SenderId", apiNotificationPush.getSenderId());
        assertEquals("TokenId", notificationPush.getToken());
    }
}