package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;

import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSchedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiNotification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActionsApiResponseMapperTest {

    public static final String URL_GOOGLE = "http://www.google.com";
    public static final String ACTION_BROWSER = "browser";

    @Mock
    ApiNotification apiNotification;

    @Mock ApiSchedule apiSchedule;

    @Mock ActionNotificationExternalClassToModelMapper actionNotificationResponseMapper;

    @Mock ActionScheduledExternalClassToModelMapper actionScheduledExternalClassToModelMapper;

    @Mock
    OrchextraNotification notification;

    @Mock Schedule schedule;

    @Test
    public void testDataToModelOk() throws Exception {
        ApiActionData apiActionData = new ApiActionData();
        apiActionData.setUrl(URL_GOOGLE);
        apiActionData.setType(ACTION_BROWSER);
        apiActionData.setNotification(apiNotification);

        when(actionNotificationResponseMapper.externalClassToModel(isA(ApiNotification.class))).thenReturn(
            notification);
        when(actionScheduledExternalClassToModelMapper.externalClassToModel(isA(ApiSchedule.class))).thenReturn(schedule);

        ActionsApiExternalClassToModelMapper
            apiResponseMapper = new ActionsApiExternalClassToModelMapper(actionNotificationResponseMapper, actionScheduledExternalClassToModelMapper);
        BasicAction basicAction = apiResponseMapper.externalClassToModel(apiActionData);

        assertEquals(URL_GOOGLE, basicAction.getUrl());
        assertEquals(ActionType.BROWSER.getStringValue(), basicAction.getActionType().getStringValue());
    }

    @Test
    public void testDataToModelNullURL() throws Exception {
        ApiActionData apiActionData = new ApiActionData();
        apiActionData.setType(ACTION_BROWSER);
        apiActionData.setNotification(apiNotification);

        when(actionNotificationResponseMapper.externalClassToModel(isA(ApiNotification.class))).thenReturn(
            notification);
        when(actionScheduledExternalClassToModelMapper.externalClassToModel(isA(ApiSchedule.class))).thenReturn(schedule);

        ActionsApiExternalClassToModelMapper
            apiResponseMapper = new ActionsApiExternalClassToModelMapper(actionNotificationResponseMapper, actionScheduledExternalClassToModelMapper);
        BasicAction basicAction = apiResponseMapper.externalClassToModel(apiActionData);

        assertNull(basicAction.getUrl());
        assertEquals(ActionType.BROWSER.getStringValue(), basicAction.getActionType().getStringValue());
    }

    @Test
    public void testDataToModelEmptyAction() throws Exception {
        ApiActionData apiActionData = new ApiActionData();
        apiActionData.setUrl(URL_GOOGLE);
        apiActionData.setType("");
        apiActionData.setNotification(apiNotification);

        when(actionNotificationResponseMapper.externalClassToModel(isA(ApiNotification.class))).thenReturn(
            notification);
        when(actionScheduledExternalClassToModelMapper.externalClassToModel(isA(ApiSchedule.class))).thenReturn(schedule);

        ActionsApiExternalClassToModelMapper
            apiResponseMapper = new ActionsApiExternalClassToModelMapper(actionNotificationResponseMapper, actionScheduledExternalClassToModelMapper);
        BasicAction basicAction = apiResponseMapper.externalClassToModel(apiActionData);

        assertEquals(URL_GOOGLE, basicAction.getUrl());
        assertEquals(ActionType.NOT_DEFINED.getStringValue(), basicAction.getActionType().getStringValue());
    }

    @Test
    public void testDataToModelNullAction() throws Exception {
        ApiActionData apiActionData = new ApiActionData();
        apiActionData.setUrl(URL_GOOGLE);
        apiActionData.setNotification(apiNotification);

        when(actionNotificationResponseMapper.externalClassToModel(isA(ApiNotification.class))).thenReturn(
            notification);
        when(actionScheduledExternalClassToModelMapper.externalClassToModel(isA(ApiSchedule.class))).thenReturn(schedule);

        ActionsApiExternalClassToModelMapper apiResponseMapper = new ActionsApiExternalClassToModelMapper(
            actionNotificationResponseMapper, actionScheduledExternalClassToModelMapper);

        BasicAction basicAction = apiResponseMapper.externalClassToModel(apiActionData);

        assertEquals(URL_GOOGLE, basicAction.getUrl());
        assertEquals(ActionType.NOT_DEFINED.getStringValue(), basicAction.getActionType().getStringValue());
    }
}