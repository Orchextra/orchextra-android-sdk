package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiSdkAuthData;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public class SdkApiExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiSdkAuthData, SdkAuthData> {

    @Override
    public SdkAuthData externalClassToModel(ApiSdkAuthData apiSdkAuthData) {

        SdkAuthData sdkAuthData = new SdkAuthData(
            apiSdkAuthData.getValue(),
            apiSdkAuthData.getExpiresIn(),
            apiSdkAuthData.getProjectId()
        );

        return sdkAuthData;
    }
}
