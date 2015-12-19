package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.actions.strategy.Notification;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class ActionsApiResponseMapper implements ResponseMapper<BasicAction, ApiActionData> {

  private final ActionNotificationResponseMapper actionNotificationResponseMapper;

  public ActionsApiResponseMapper(ActionNotificationResponseMapper
      actionNotificationResponseMapper) {
    this.actionNotificationResponseMapper = actionNotificationResponseMapper;
  }

  @Override public BasicAction dataToModel(ApiActionData apiActionData) {

    ActionType actionType = ActionType.getActionTypeValue(apiActionData.getType());
    String url = apiActionData.getUrl();

    Notification notification = MapperUtils.checkNullDataResponse(actionNotificationResponseMapper,
        apiActionData.getNotification());

    return new ApiActionData.ActionBuilder(actionType, url, notification).build();

  }

}
