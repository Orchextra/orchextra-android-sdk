package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.builders.ApiBeaconBuilder;
import gigigo.com.orchextra.data.datasources.builders.ApiGeofenceBuilder;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiBeacon;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiTheme;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ConfigApiResponseMapperTest {

    @Mock
    ApiTheme apiTheme;

    @Mock
    ApiVuforia apiVuforia;

    @Mock VuforiaResponseMapper vuforiaResponseMapper;

    @Mock ThemeResponseMapper themeResponseMapper;

    BeaconResponseMapper beaconResponseMapper = new BeaconResponseMapper();

    GeofenceResponseMapper geofenceResponseMapper = new GeofenceResponseMapper(new PointMapper());

    @Test
    public void testDataToModelOk() throws Exception {
        List<ApiGeofence> apiGeofencesList = new ArrayList<>();
        apiGeofencesList.add(ApiGeofenceBuilder.Builder().build());

        ApiBeacon apiBeacon = ApiBeaconBuilder.Builder().build();
        List<ApiBeacon> apiBeaconList = new ArrayList<>();
        apiBeaconList.add(apiBeacon);

        ApiConfigData apiConfigData = new ApiConfigData();

        apiConfigData.setGeoMarketing(apiGeofencesList);
        apiConfigData.setProximity(apiBeaconList);
        apiConfigData.setTheme(apiTheme);
        apiConfigData.setVuforia(apiVuforia);
        apiConfigData.setRequestWaitTime(3000);

        ConfigApiResponseMapper mapper = new ConfigApiResponseMapper(vuforiaResponseMapper, themeResponseMapper,
                beaconResponseMapper, geofenceResponseMapper);

        ConfigInfoResult configInfoResult = mapper.dataToModel(apiConfigData);

        assertEquals(1, configInfoResult.getGeofences().size());
        assertEquals(23.45, configInfoResult.getGeofences().get(0).getPoint().getLat(), 0.001);
        assertEquals(56.45, configInfoResult.getGeofences().get(0).getPoint().getLng(), 0.001);
        assertEquals(30, configInfoResult.getGeofences().get(0).getRadius(), 0.001);
        assertEquals(1, configInfoResult.getBeacons().size());
        assertEquals(ApiBeaconBuilder.NAME, configInfoResult.getBeacons().get(0).getName());
        assertEquals(ApiBeaconBuilder.CODE, configInfoResult.getBeacons().get(0).getCode());
        assertEquals(ApiBeaconBuilder.MAJOR, configInfoResult.getBeacons().get(0).getMajor());
        assertEquals(3000, configInfoResult.getRequestWaitTime());
    }
}