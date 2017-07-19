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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiEddyStoneRequest;
import java.util.HashMap;
import java.util.Map;


public class ActionQueryModelToExternalClassMapper
        implements ModelToExternalClassMapper<Trigger, Map<String, String>> {

    private final static String TYPE_QUERY_PARAM = "type";
    private final static String VALUE_QUERY_PARAM = "value";
    private final static String LAT_QUERY_PARAM = "lat";
    private final static String LNG_QUERY_PARAM = "lng";
    private final static String EVENT_QUERY_PARAM = "event";
    private final static String PHONE_STATUS_QUERY_PARAM = "phoneStatus";
    private final static String DISTANCE_QUERY_PARAM = "distance";
    //--------------------------tlm
    private final static String TLM_TEMPERATURE_QUERY_PARAM = "temperature";
    private final static String TLM_BATTERY_QUERY_PARAM = "battery";
    private final static String TLM_UPTIME_QUERY_PARAM = "uptime";
    private final static String TLM_PDUCOUNT_QUERY_PARAM = "pducount";
    private final static String TLM_TLMVERSION_QUERY_PARAM = "tlmversion";
    private final static String EDDYSTONE_URL_QUERY_PARAM = "url";

    @Override
    public Map<String, String> modelToExternalClass(Trigger trigger) {

        Map<String, String> query = new HashMap<>();

        query.put(TYPE_QUERY_PARAM, trigger.getTriggerType().getStringValue());
        query.put(VALUE_QUERY_PARAM, trigger.getCode());

        if (trigger.geoPointIsSupported()) {
            query.put(LAT_QUERY_PARAM, String.valueOf(trigger.getPoint().getLat()));
            query.put(LNG_QUERY_PARAM, String.valueOf(trigger.getPoint().getLng()));
        }

        if (trigger.beaconDistanceTypeIsSupported()) {
            query.put(DISTANCE_QUERY_PARAM,
                    String.valueOf(trigger.getBeaconDistanceType().getStringValue()));
        }

        if (trigger.getGeoPointEventType() != null) {
            query.put(EVENT_QUERY_PARAM, trigger.getGeoPointEventType().getStringValue());
        }

        query.put(PHONE_STATUS_QUERY_PARAM, trigger.getAppRunningModeType().getStringValue());

        if (trigger.getGeoDistance() > 0) {
            query.put(DISTANCE_QUERY_PARAM, String.valueOf(trigger.getGeoDistance()));
        }

        if(trigger.geoEddyStoneTlmIsSupported()){
            query.put(TLM_BATTERY_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getBattery()));
            query.put(TLM_PDUCOUNT_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getPducount()));
            query.put(TLM_TEMPERATURE_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getTemperature()));
            query.put(TLM_TLMVERSION_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getTlmversion()));
            query.put(TLM_UPTIME_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getUptime()));
            query.put(EDDYSTONE_URL_QUERY_PARAM,String.valueOf(trigger.getEddyStoneTlmData().getUrl()));
        }
        return query;
    }

    //fixme asv when queryString become deprecated, will need, no only ApiEddyStoneReuquest, we needsomething new
    // like ApiActionRequest!
    public ApiEddyStoneRequest modelToExternalClass2(Trigger trigger) {
         //fixme do the json body
        return new ApiEddyStoneRequest();
    }
}
