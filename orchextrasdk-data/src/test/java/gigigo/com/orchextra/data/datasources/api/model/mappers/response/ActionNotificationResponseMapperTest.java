package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.actions.strategy.Notification;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiNotification;

import static org.junit.Assert.assertEquals;

public class ActionNotificationResponseMapperTest {

    public static final String TITULO = "Titulo";
    public static final String BODY = "Texto de prueba";

    @Test
    public void testDataToModel() throws Exception {
        ApiNotification apiNotification = new ApiNotification();
        apiNotification.setTitle(TITULO);
        apiNotification.setBody(BODY);

        ActionNotificationExternalClassToModelMapper
            actionNotificationResponseMapper = new ActionNotificationExternalClassToModelMapper();
        Notification notification = actionNotificationResponseMapper.externalClassToModel(
            apiNotification);

        assertEquals(TITULO, notification.getTitle());
        assertEquals(BODY, notification.getBody());
    }
}