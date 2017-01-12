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

package com.gigigo.orchextra.domain.model.triggers.strategy.types;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraTLMEddyStoneBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.triggers.params.TriggerType;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.BeaconDistanceTypeBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.EddyStoneTlmBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoDistanceBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviour;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointBehaviourImpl;
import com.gigigo.orchextra.domain.model.triggers.strategy.behaviours.GeoPointEventTypeBehaviour;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

public abstract class Trigger {

    //Mandatory
    private final TriggerType triggerType;
    //Mandatory
    private final String code;
    //Mandatory
    private final AppRunningModeType appRunningModeType;
    //Mandatory
    protected boolean isTriggerable;

    //Mandatory
    private final GeoPointBehaviour geoPointBehaviour;

    protected GeoPointEventTypeBehaviour geoPointEventTypeBehaviour;
    protected BeaconDistanceTypeBehaviour beaconDistanceTypeBehaviour;
    protected GeoDistanceBehaviour geoDistanceBehaviour;
    protected EddyStoneTlmBehaviour eddyStoneTlmBehaviour;

    //asv 4 include new trigger 4 eddystone beacons
    //option A Create new EddyStoneBehavior with tlm data inside (issuported in trigger)
    //option B keep tlmdata in trigger and trigger.tlm!=null parsing that
    //option C   option A + option B
    // 11/01/17 option A!

    Trigger(TriggerType triggerType, String code, OrchextraLocationPoint point,
            AppRunningModeType appRunningModeType) {
        this.triggerType = triggerType;
        this.code = code;
        this.appRunningModeType = appRunningModeType;
        this.geoPointBehaviour = new GeoPointBehaviourImpl(point);
    }

    public Trigger() {
        this.triggerType = null;
        this.code = null;
        this.appRunningModeType = null;
        this.geoPointBehaviour = null;
    }

    abstract void setConcreteBehaviour();

    public static GeofenceTrigger createGeofenceTrigger(String code, OrchextraLocationPoint point,
                                                        AppRunningModeType appRunningModeType, double distance, GeoPointEventType geoPointEventType) {

        GeofenceTrigger geofenceTrigger =
                new GeofenceTrigger(code, point, appRunningModeType, distance, geoPointEventType);
        geofenceTrigger.setConcreteBehaviour();
        return geofenceTrigger;
    }

    public static Trigger createQrScanTrigger(String id, OrchextraLocationPoint point) {
        return createScanTrigger(id, TriggerType.QR, point);
    }

    public static Trigger createBarcodeScanTrigger(String id, OrchextraLocationPoint point) {
        return createScanTrigger(id, TriggerType.BARCODE, point);
    }

    public static Trigger createVuforiaScanTrigger(String id, OrchextraLocationPoint point) {
        return createScanTrigger(id, TriggerType.VUFORIA, point);
    }

    private static Trigger createScanTrigger(String id, TriggerType scanType, OrchextraLocationPoint point) {
        try {
            ScanTrigger scanTrigger = new ScanTrigger(scanType, id, point, AppRunningModeType.FOREGROUND);
            scanTrigger.setConcreteBehaviour();
            return scanTrigger;
        } catch (Exception e) {
            return Trigger.createEmptyTrigger();
        }
    }

    public static Trigger createBeaconRegionTrigger(AppRunningModeType appRunningMode,
                                                    OrchextraRegion orchextraRegion) {

        try {
            BeaconRegionTrigger beaconRegionTrigger =
                    new BeaconRegionTrigger(orchextraRegion, appRunningMode);
            beaconRegionTrigger.setConcreteBehaviour();
            return beaconRegionTrigger;
        } catch (Exception e) {
            return Trigger.createEmptyTrigger();
        }
    }

    public static Trigger createBeaconTrigger(AppRunningModeType appRunningMode,
                                              OrchextraBeacon orchextraBeacon) {
        try {
            BeaconTrigger beaconTrigger = new BeaconTrigger(orchextraBeacon, appRunningMode);
            beaconTrigger.setConcreteBehaviour();
            return beaconTrigger;
        } catch (Exception e) {
            return Trigger.createEmptyTrigger();
        }
    }

    public static Trigger createEddyStoneBeaconTrigger(AppRunningModeType appRunningMode,
                                                       OrchextraBeacon orchextraBeacon) {
        try {
            EddyStoneBeaconTrigger eddyStoneBeaconTrigger = new EddyStoneBeaconTrigger(orchextraBeacon, appRunningMode);
            eddyStoneBeaconTrigger.setConcreteBehaviour();
            return eddyStoneBeaconTrigger;
        } catch (Exception e) {
            return Trigger.createEmptyTrigger();
        }
    }


    public static Trigger createImageRecognitionTrigger(String content, OrchextraLocationPoint orchextraLocationPoint) {
        try {
            ImageRecognitionTrigger imageRecognitionTrigger =
                    new ImageRecognitionTrigger(content, orchextraLocationPoint, AppRunningModeType.FOREGROUND);
            imageRecognitionTrigger.setConcreteBehaviour();
            return imageRecognitionTrigger;
        } catch (Exception e) {
            return Trigger.createEmptyTrigger();
        }
    }

    public TriggerType getTriggerType() {
        return triggerType;
    }

    public String getCode() {
        return code;
    }

    public OrchextraLocationPoint getPoint() {
        return geoPointBehaviour.getPoint();
    }

    public AppRunningModeType getAppRunningModeType() {
        return appRunningModeType;
    }

    public GeoPointEventType getGeoPointEventType() {
        return geoPointEventTypeBehaviour.getGeoPointEventType();
    }

    public BeaconDistanceType getBeaconDistanceType() {
        return beaconDistanceTypeBehaviour.getBeaconDistanceType();
    }

    public boolean beaconDistanceTypeIsSupported() {
        return beaconDistanceTypeBehaviour.isSupported();
    }

    public double getGeoDistance() {
        return geoDistanceBehaviour.getGeoDistance();
    }

    public boolean geoPointIsSupported() {
        return geoPointBehaviour.isSupported();
    }

    public OrchextraTLMEddyStoneBeacon getEddyStoneTlmData() {
        return eddyStoneTlmBehaviour.getEddyStoneTlmData();
    }

    public boolean geoEddyStoneTlmIsSupported() {
        return eddyStoneTlmBehaviour.isSupported();
    }

    private static Trigger createEmptyTrigger() {
        Trigger trigger = new Trigger() {
            @Override
            void setConcreteBehaviour() {
                this.beaconDistanceTypeBehaviour = null;
                this.geoPointEventTypeBehaviour = null;
                this.geoDistanceBehaviour = null;
                this.eddyStoneTlmBehaviour = null;
            }
        };

        return trigger;
    }
}
