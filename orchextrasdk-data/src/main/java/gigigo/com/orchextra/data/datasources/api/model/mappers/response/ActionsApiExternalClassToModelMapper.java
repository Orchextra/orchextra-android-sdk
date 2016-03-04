/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.MapperUtils;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionData;


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

    if (apiActionData == null) {
      return new EmptyAction();
    }

    ActionType actionType = ActionType.getActionTypeValue(apiActionData.getType());
    String url = apiActionData.getUrl();

    String id = apiActionData.getId();
    String tid = apiActionData.getTrackId();

    OrchextraNotification notification =
        (OrchextraNotification) MapperUtils.checkNullDataResponse(actionNotificationResponseMapper,
            apiActionData.getNotification());

    Schedule schedule = (Schedule) MapperUtils.checkNullDataResponse(actionScheduleResponseMapper,
        apiActionData.getSchedule());

    return new BasicAction.ActionBuilder(id, tid, actionType, url, notification, schedule).build();
  }
}
