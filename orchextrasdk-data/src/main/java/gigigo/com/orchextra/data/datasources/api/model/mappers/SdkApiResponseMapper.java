package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import java.util.Date;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class SdkApiResponseMapper implements ResponseMapper<SdkAuthData, ApiSdkAuthData> {

    @Override
    public SdkAuthData dataToModel(ApiSdkAuthData apiSdkAuthData) {
        SdkAuthData sdkAuthData = new SdkAuthData();

        Date expiredAtDate = DateUtils.stringToDateWithFormat(apiSdkAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT);
        sdkAuthData.setExpiresAt(expiredAtDate);

<<<<<<< Updated upstream
    sdkAuthData.setExpiresIn(apiSdkAuthData.getExpiresIn());
    sdkAuthData.setProjectId(apiSdkAuthData.getProjectId());
    sdkAuthData.setValue(apiSdkAuthData.getValue());
    return sdkAuthData;
  }
=======
        sdkAuthData.setExpiresIn(apiSdkAuthData.getExpiresIn());
        sdkAuthData.setProjectId(apiSdkAuthData.getProjectId());
        sdkAuthData.setValue(apiSdkAuthData.getValue());

        return sdkAuthData;
    }

    @Override
    public ApiSdkAuthData modelToData(SdkAuthData sdkAuthData) {
        ApiSdkAuthData apiSdkAuthData = new ApiSdkAuthData();
        apiSdkAuthData.setValue(sdkAuthData.getValue());

        String expiredAtString = DateUtils.dateToStringWithFormat(sdkAuthData.getExpiresAt(), DateFormatConstants.DATE_FORMAT);
        apiSdkAuthData.setExpiresAt(expiredAtString);

        apiSdkAuthData.setExpiresIn(sdkAuthData.getExpiresIn());
        apiSdkAuthData.setProjectId(sdkAuthData.getProjectId());
        return apiSdkAuthData;
    }
>>>>>>> Stashed changes
}
