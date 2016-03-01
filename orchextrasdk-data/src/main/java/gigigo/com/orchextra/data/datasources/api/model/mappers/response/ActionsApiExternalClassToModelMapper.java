package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ActionsApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiActionData, BasicAction> {

  private final ExternalClassToModelMapper actionNotificationResponseMapper;
  private final ExternalClassToModelMapper actionScheduleResponseMapper;

  public ActionsApiExternalClassToModelMapper(
      ExternalClassToModelMapper actionNotificationResponseMapper,
      ExternalClassToModelMapper actionScheduleResponseMapper) {
    this.actionNotificationResponseMapper = actionNotificationResponseMapper;
    this.actionScheduleResponseMapper = actionScheduleResponseMapper;
  }

  @Override public BasicAction externalClassToModel(ApiActionData apiActionData) {

    if (apiActionData == null){
      return  new EmptyAction();
    }

    ActionType actionType = ActionType.getActionTypeValue(apiActionData.getType());
    String url = apiActionData.getUrl();

    String id = apiActionData.getId();
    String tid = apiActionData.getTrackId();

    Notification notification = (Notification) MapperUtils.checkNullDataResponse(actionNotificationResponseMapper,
        apiActionData.getNotification());

    Schedule schedule = (Schedule) MapperUtils.checkNullDataResponse(actionScheduleResponseMapper,
        apiActionData.getSchedule());

    return new BasicAction.ActionBuilder(id, tid, actionType, url, notification, schedule).build();

  }

}
