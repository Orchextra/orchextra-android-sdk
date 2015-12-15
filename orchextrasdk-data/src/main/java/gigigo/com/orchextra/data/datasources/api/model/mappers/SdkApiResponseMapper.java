package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.orchextra.domain.entities.SdkAuthData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class SdkApiResponseMapper implements Mapper<SdkAuthData, ApiSdkAuthData> {

  @Override public ApiSdkAuthData modelToData(SdkAuthData sdkAuthData) {
    ApiSdkAuthData apiSdkAuthData = new ApiSdkAuthData();
    apiSdkAuthData.setValue(sdkAuthData.getValue());
    //TODO create and use GGGLib DateUtils Ex:  DateUtils.dateToStringWithFormat(sdkAuthData.getExpiresAt(), FORMAT)
    apiSdkAuthData.setExpiresAt("2016-12-15T18:21:06Z");
    apiSdkAuthData.setExpiresIn(sdkAuthData.getExpiresIn());
    apiSdkAuthData.setProjectId(sdkAuthData.getProjectId());
    return apiSdkAuthData;
  }

  @Override public SdkAuthData dataToModel(ApiSdkAuthData apiSdkAuthData) {
    SdkAuthData sdkAuthData = new SdkAuthData();
    //TODO create and use GGGLib DateUtils Ex:  DateUtils.stringToDateWithFormat(apiSdkAuthData.getExpiresAt(), FORMAT)
    sdkAuthData.setExpiresAt(null);
    sdkAuthData.setExpiresIn(apiSdkAuthData.getExpiresIn());
    sdkAuthData.setProjectId(apiSdkAuthData.getProjectId());
    sdkAuthData.setValue(apiSdkAuthData.getValue());
    return sdkAuthData;
  }
}
