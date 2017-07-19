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

package com.gigigo.orchextra.di.modules.data;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ActionNotificationResponse;
import com.gigigo.orchextra.di.qualifiers.ActionQueryRequest;
import com.gigigo.orchextra.di.qualifiers.ActionScheduleResponse;
import com.gigigo.orchextra.di.qualifiers.ActionsResponse;
import com.gigigo.orchextra.di.qualifiers.AppRequest;
import com.gigigo.orchextra.di.qualifiers.AvailableCustomFieldResponse;
import com.gigigo.orchextra.di.qualifiers.BeaconResponse;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigRequest;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.CrmCustomFieldsResponse;
import com.gigigo.orchextra.di.qualifiers.CrmRequest;
import com.gigigo.orchextra.di.qualifiers.DeviceCustomFieldsResponse;
import com.gigigo.orchextra.di.qualifiers.DeviceRequest;
import com.gigigo.orchextra.di.qualifiers.EddyStoneBeaconResponse;
import com.gigigo.orchextra.di.qualifiers.GeoLocationRequest;
import com.gigigo.orchextra.di.qualifiers.GeofenceResponse;
import com.gigigo.orchextra.di.qualifiers.PointReqResMapper;
import com.gigigo.orchextra.di.qualifiers.PushNotificationRequest;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.VuforiaResponse;
import gigigo.com.orchextra.data.datasources.api.model.mappers.PointMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.ActionQueryModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.AppModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.ConfigModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.CrmModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.DeviceModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.GeoLocationModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.request.PushNotificationModelToExternalClassMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ActionNotificationExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ActionScheduledExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ActionsApiExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.AvailableCustomFieldExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.BeaconExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ClientApiExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.ConfigApiExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.CrmCustomFieldsExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.DeviceCustomFieldsExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.EddyStoneBeaconExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.GeofenceExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.OrchextraGenericResponseMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.SdkApiExternalClassToModelMapper;
import gigigo.com.orchextra.data.datasources.api.model.mappers.response.VuforiaExternalClassToModelMapper;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Singleton;


@Module
public class ApiMappersModule {

    //region Response Mappers

    @Provides
    @Singleton
    @SdkDataResponseMapper
    ApiGenericResponseMapper
    provideSdkDataResponseMapper(SdkApiExternalClassToModelMapper sdkMapper) {
        return createResponseMapper(sdkMapper);
    }

    @Provides
    @Singleton
    @ClientDataResponseMapper
    ApiGenericResponseMapper
    provideClientDataResponseMapper(ClientApiExternalClassToModelMapper clientMapper) {
        return createResponseMapper(clientMapper);
    }

    @Provides
    @Singleton
    @ConfigResponseMapper
    ApiGenericResponseMapper
    provideConfigResponseMapper(ConfigApiExternalClassToModelMapper configApiResponseMapper) {
        return createResponseMapper(configApiResponseMapper);
    }

    @Provides
    @Singleton
    SdkApiExternalClassToModelMapper provideSdkMapper() {
        return new SdkApiExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    ClientApiExternalClassToModelMapper provideClientMapper() {
        return new ClientApiExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    @ActionsResponse
    ApiGenericResponseMapper provideActionsResMapper(
            @ActionsResponse ExternalClassToModelMapper actionsApiResponseMapper) {
        return createResponseMapper(actionsApiResponseMapper);
    }

    @Provides
    @Singleton
    @ActionsResponse
    ExternalClassToModelMapper provideActionsApiResponseMapper(
            @ActionNotificationResponse ExternalClassToModelMapper actionNotifResponseMapper,
            @ActionScheduleResponse ExternalClassToModelMapper actionScheduleResponseMapper) {
        return new ActionsApiExternalClassToModelMapper(actionNotifResponseMapper, actionScheduleResponseMapper);
    }

    @Provides
    @Singleton
    @ActionNotificationResponse
    ExternalClassToModelMapper provideActionNotificationResponseMapper() {
        return new ActionNotificationExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    @ActionScheduleResponse
    ExternalClassToModelMapper provideActionScheduleResponseMapper() {
        return new ActionScheduledExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    ConfigApiExternalClassToModelMapper provideConfigApiResponseMapper(
        @BeaconResponse BeaconExternalClassToModelMapper beaconResponse,
        @EddyStoneBeaconResponse EddyStoneBeaconExternalClassToModelMapper eddyStoneExternalBeaconResponse,
            @GeofenceResponse GeofenceExternalClassToModelMapper geofenceResponseMapper,
            @VuforiaResponse VuforiaExternalClassToModelMapper vuforiaResponseMapper,
            @AvailableCustomFieldResponse AvailableCustomFieldExternalClassToModelMapper availableCustomFieldResponseMapper,
            @CrmCustomFieldsResponse CrmCustomFieldsExternalClassToModelMapper crmCustomFieldsResponseMapper,
            @DeviceCustomFieldsResponse DeviceCustomFieldsExternalClassToModelMapper deviceCustomFieldsResponseMapper) {

        return new ConfigApiExternalClassToModelMapper(vuforiaResponseMapper,
                beaconResponse,eddyStoneExternalBeaconResponse, geofenceResponseMapper,
                availableCustomFieldResponseMapper,
                crmCustomFieldsResponseMapper,
                deviceCustomFieldsResponseMapper);
    }

    @Provides
    @Singleton
    @VuforiaResponse
    VuforiaExternalClassToModelMapper provideVuforiaResponseMapper() {
        return new VuforiaExternalClassToModelMapper();
    }
    @Provides
    @Singleton
    @EddyStoneBeaconResponse
    EddyStoneBeaconExternalClassToModelMapper provideEddyStoneBeaconResponseMapper() {
        return new EddyStoneBeaconExternalClassToModelMapper();
    }
    @Provides
    @Singleton
    @AvailableCustomFieldResponse AvailableCustomFieldExternalClassToModelMapper provideAvailableCustomFieldExternalClassToModelMapper() {
        return new AvailableCustomFieldExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    @CrmCustomFieldsResponse CrmCustomFieldsExternalClassToModelMapper provideCrmCustomFieldsExternalClassToModelMapper() {
        return new CrmCustomFieldsExternalClassToModelMapper();
    }

    @Provides
    @Singleton
    @DeviceCustomFieldsResponse DeviceCustomFieldsExternalClassToModelMapper provideDeviceCustomFieldsExternalClassToModelMapper() {
        return new DeviceCustomFieldsExternalClassToModelMapper();
    }


    @Provides
    @Singleton
    @GeofenceResponse
    GeofenceExternalClassToModelMapper provideGeofenceResponseMapper(
            @PointReqResMapper PointMapper pointMapper) {
        return new GeofenceExternalClassToModelMapper(pointMapper);
    }

    @Provides
    @Singleton
    @BeaconResponse
    BeaconExternalClassToModelMapper provideBeaconResponseMapper() {
        return new BeaconExternalClassToModelMapper();
    }

    private ApiGenericResponseMapper createResponseMapper(ExternalClassToModelMapper mapper) {
        return new OrchextraGenericResponseMapper(mapper);
    }

    //endregion

    @Provides
    @Singleton
    @PointReqResMapper
    PointMapper providePointMapper() {
        return new PointMapper();
    }

    //region Request Mappers

    @Provides
    @Singleton
    @ActionQueryRequest
    ModelToExternalClassMapper provideActionQueryRequestMapper() {
        return new ActionQueryModelToExternalClassMapper();
    }

    @Provides
    @Singleton
    @AppRequest
    AppModelToExternalClassMapper provideAppRequestMapper() {
        return new AppModelToExternalClassMapper();
    }

    @Provides
    @Singleton
    @CrmRequest
    CrmModelToExternalClassMapper provideCrmRequestMapper() {
        return new CrmModelToExternalClassMapper();
    }

    @Provides
    @Singleton
    @PushNotificationRequest
    PushNotificationModelToExternalClassMapper providePushNotifRequestMapper() {
        return new PushNotificationModelToExternalClassMapper();
    }

    @Provides
    @Singleton
    @DeviceRequest
    DeviceModelToExternalClassMapper provideDeviceRequestMapper() {
        return new DeviceModelToExternalClassMapper();
    }

    @Provides
    @Singleton
    @GeoLocationRequest
    GeoLocationModelToExternalClassMapper provideGeoLocationRequestMapper(
            @PointReqResMapper PointMapper pointMapper) {
        return new GeoLocationModelToExternalClassMapper(pointMapper);
    }

    @Provides
    @Singleton
    @ConfigRequest
    ModelToExternalClassMapper provideConfigRequestMapper(
            @PushNotificationRequest
            PushNotificationModelToExternalClassMapper pushNotificationRequestMapper,
            @GeoLocationRequest GeoLocationModelToExternalClassMapper geoLocationRequestMapper,
            @DeviceRequest DeviceModelToExternalClassMapper deviceRequestMapper,
            @CrmRequest CrmModelToExternalClassMapper crmRequestMapper,
            @AppRequest AppModelToExternalClassMapper appRequestMapper) {

        return new ConfigModelToExternalClassMapper(pushNotificationRequestMapper, geoLocationRequestMapper,
                deviceRequestMapper, crmRequestMapper, appRequestMapper);
    }

    //endregion
}
