package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.api.model.mappers.request.CrmModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.DeviceModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.builders.ApiRegionBuilder;
import gigigo.com.orchextra.data.datasources.builders.ApiGeofenceBuilder;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiRegion;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforiaCredentials;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConfigRequestApiResponseMapperTest {

    @Mock
    ApiVuforiaCredentials apiVuforiaCredentials;

    @Mock
    VuforiaExternalClassToModelMapper vuforiaResponseMapper;

    BeaconExternalClassToModelMapper beaconResponseMapper = new BeaconExternalClassToModelMapper();

    GeofenceExternalClassToModelMapper
            geofenceResponseMapper = new GeofenceExternalClassToModelMapper(new PointMapper());

    AvailableCustomFieldExternalClassToModelMapper availableCustomFieldResponseMapper= new AvailableCustomFieldExternalClassToModelMapper();
    CrmCustomFieldsExternalClassToModelMapper crmCustomFieldsResponseMapper= new CrmCustomFieldsExternalClassToModelMapper();
    DeviceCustomFieldsExternalClassToModelMapper deviceCustomFieldsResponseMapper = new DeviceCustomFieldsExternalClassToModelMapper();


    @Test
    public void testDataToModelOk() throws Exception {
        List<ApiGeofence> apiGeofencesList = new ArrayList<>();
        apiGeofencesList.add(ApiGeofenceBuilder.Builder().build());

        ApiRegion apiRegion = ApiRegionBuilder.Builder().build();
        List<ApiRegion> apiRegionList = new ArrayList<>();
        apiRegionList.add(apiRegion);

        ApiConfigData apiConfigData = new ApiConfigData();

        apiConfigData.setGeoMarketing(apiGeofencesList);
        apiConfigData.setProximity(apiRegionList);
        apiConfigData.setVuforia(apiVuforiaCredentials);
        apiConfigData.setRequestWaitTime(120);

        ConfigApiExternalClassToModelMapper mapper =
                new ConfigApiExternalClassToModelMapper(vuforiaResponseMapper,
                        beaconResponseMapper, geofenceResponseMapper,
                        availableCustomFieldResponseMapper,
                        crmCustomFieldsResponseMapper,
                        deviceCustomFieldsResponseMapper);

        ConfigurationInfoResult configurationInfoResult = mapper.externalClassToModel(apiConfigData);

        assertEquals(1, configurationInfoResult.getGeofences().size());
        assertEquals(23.45, configurationInfoResult.getGeofences().get(0).getPoint().getLat(), 0.001);
        assertEquals(56.45, configurationInfoResult.getGeofences().get(0).getPoint().getLng(), 0.001);
        assertEquals(30, configurationInfoResult.getGeofences().get(0).getRadius(), 0.001);
        assertEquals(1, configurationInfoResult.getRegions().size());
        assertEquals(ApiRegionBuilder.CODE, configurationInfoResult.getRegions().get(0).getCode());
        assertEquals(ApiRegionBuilder.MAJOR, configurationInfoResult.getRegions().get(0).getMajor());
        assertEquals(120000, configurationInfoResult.getRequestWaitTime());
    }
}