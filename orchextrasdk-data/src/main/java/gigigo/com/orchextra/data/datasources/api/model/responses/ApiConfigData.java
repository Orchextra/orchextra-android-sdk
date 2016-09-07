/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;


public class ApiConfigData {

    @Expose
    @SerializedName("geoMarketing")
    private List<ApiGeofence> geoMarketing;

    @Expose
    @SerializedName("proximity")
    private List<ApiRegion> proximity;

    @Expose
    @SerializedName("vuforia")
    private ApiVuforiaCredentials vuforia;

    @Expose
    @SerializedName("requestWaitTime")
    private int requestWaitTime;

    @Expose
    @SerializedName("availableCustomFields")
    private Map<String, ApiAvailableCustomFieldType> availableCustomFields;

    @Expose
    @SerializedName("crm")
    private ApiCrmCustomFields crm;

    @Expose
    @SerializedName("device")
    private ApiDeviceCustomFields device;

    public List<ApiGeofence> getGeoMarketing() {
        return geoMarketing;
    }

    public void setGeoMarketing(List<ApiGeofence> geoMarketing) {
        this.geoMarketing = geoMarketing;
    }

    public List<ApiRegion> getProximity() {
        return proximity;
    }

    public void setProximity(List<ApiRegion> proximity) {
        this.proximity = proximity;
    }

    public ApiVuforiaCredentials getVuforia() {
        return vuforia;
    }

    public void setVuforia(ApiVuforiaCredentials vuforia) {
        this.vuforia = vuforia;
    }

    public int getRequestWaitTime() {
        return requestWaitTime;
    }

    public void setRequestWaitTime(int requestWaitTime) {
        this.requestWaitTime = requestWaitTime;
    }

    public Map<String, ApiAvailableCustomFieldType> getAvailableCustomFields() {
        return availableCustomFields;
    }

    public ApiCrmCustomFields getCrm() {
        return crm;
    }

    public ApiDeviceCustomFields getDevice() {
        return device;
    }
}
