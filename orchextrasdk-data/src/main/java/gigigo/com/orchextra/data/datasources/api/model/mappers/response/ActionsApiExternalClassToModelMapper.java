package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.Notification;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ActionsApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiActionData, BasicAction> {

  private final ActionNotificationExternalClassToModelMapper actionNotificationResponseMapper;

  public ActionsApiExternalClassToModelMapper(
      ActionNotificationExternalClassToModelMapper actionNotificationResponseMapper) {
    this.actionNotificationResponseMapper = actionNotificationResponseMapper;
  }

  @Override public BasicAction externalClassToModel(ApiActionData apiActionData) {

    ActionType actionType = ActionType.getActionTypeValue(apiActionData.getType());
    String url = apiActionData.getUrl();

    Notification notification = MapperUtils.checkNullDataResponse(actionNotificationResponseMapper,
        apiActionData.getNotification());

    return new BasicAction.ActionBuilder(actionType, url, notification).build();

  }

}
