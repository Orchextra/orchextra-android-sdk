package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.network.mappers.ResponseMapper;
import com.gigigo.orchextra.domain.entities.SdkAuthData;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class SdkApiResponseMapper implements ResponseMapper<SdkAuthData, ApiSdkAuthData> {

    @Override
    public SdkAuthData dataToModel(ApiSdkAuthData apiSdkAuthData) {

        SdkAuthData sdkAuthData = new SdkAuthData(
            apiSdkAuthData.getValue(),
            apiSdkAuthData.getExpiresIn(),
            apiSdkAuthData.getProjectId()
        );

        return sdkAuthData;
    }
}
