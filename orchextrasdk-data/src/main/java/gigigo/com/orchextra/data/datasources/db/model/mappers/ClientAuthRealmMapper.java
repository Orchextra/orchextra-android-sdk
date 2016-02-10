package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import gigigo.com.orchextra.data.datasources.db.model.ClientAuthRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class ClientAuthRealmMapper implements Mapper<ClientAuthData, ClientAuthRealm> {

  @Override public ClientAuthRealm modelToExternalClass(ClientAuthData clientAuthData) {
    ClientAuthRealm clientAuthRealm = new ClientAuthRealm();
    clientAuthRealm.setExpiresAt(DateUtils.dateToStringWithFormat(
        clientAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT));
    clientAuthRealm.setExpiresIn(clientAuthData.getExpiresIn());
    clientAuthRealm.setProjectId(clientAuthData.getProjectId());
    clientAuthRealm.setUserId(clientAuthData.getUserId());
    clientAuthRealm.setValue(clientAuthData.getValue());
    return clientAuthRealm;
  }

  @Override public ClientAuthData externalClassToModel(ClientAuthRealm clientAuthRealm) {

    ClientAuthData clientAuthData = new ClientAuthData(
        clientAuthRealm.getProjectId(),
        clientAuthRealm.getUserId(),
        clientAuthRealm.getValue(),
        clientAuthRealm.getExpiresIn(),
        DateUtils.stringToDateWithFormat(
            clientAuthRealm.getExpiresAt(), DateFormatConstants.DATE_FORMAT));

    return clientAuthData;
  }
}
