package gigigo.com.orchextra.data.datasources.api.builders;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiGeofence;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiPoint;

public class ApiGeofenceBuilder {

    public static final int RADIUS = 30;
    public static final String LAT = "23.45";
    public static final String LNG = "56.45";

    public static ApiGeofenceBuilder Builder() {
        return new ApiGeofenceBuilder();
    }

    public ApiGeofence build() {
        ApiPoint apiPoint = new ApiPoint();
        apiPoint.setLat(LAT);
        apiPoint.setLng(LNG);
        ApiGeofence apiGeofence = new ApiGeofence();
        apiGeofence.setPoint(apiPoint);
        apiGeofence.setRadius(RADIUS);

        return apiGeofence;
    }
}
