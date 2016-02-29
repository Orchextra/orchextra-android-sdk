package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.Schedule;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSchedule;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/2/16.
 */
public class ActionScheduledExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiSchedule, Schedule> {

  private static final int ONE_SECOND = 1000;

  @Override public Schedule externalClassToModel(ApiSchedule apiSchedule) {

    Schedule schedule = new Schedule(apiSchedule.isCancelable(),
        apiSchedule.getSeconds()*ONE_SECOND);

    return schedule;
  }

}
