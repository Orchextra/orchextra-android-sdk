package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class SdkAuthReamlMapper implements Mapper<SdkAuthData, SdkAuthRealm> {

  @Override public SdkAuthRealm modelToExternalClass(SdkAuthData sdkAuthData) {
    SdkAuthRealm sdkAuthRealm = new SdkAuthRealm();
    sdkAuthRealm.setExpiresAt(DateUtils.dateToStringWithFormat(
        sdkAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT));
    sdkAuthRealm.setExpiresIn(sdkAuthData.getExpiresIn());
    sdkAuthRealm.setProjectId(sdkAuthData.getProjectId());
    sdkAuthRealm.setValue(sdkAuthData.getValue());
    return sdkAuthRealm;
  }

  @Override public SdkAuthData externalClassToModel(SdkAuthRealm sdkAuthRealm) {

    SdkAuthData sdkAuthdata = new SdkAuthData(
        sdkAuthRealm.getValue(),
        sdkAuthRealm.getExpiresIn(),
        DateUtils.stringToDateWithFormat(sdkAuthRealm.getExpiresAt(),
            DateFormatConstants.DATE_FORMAT),
        sdkAuthRealm.getProjectId()
    );

    return sdkAuthdata;
  }
}
