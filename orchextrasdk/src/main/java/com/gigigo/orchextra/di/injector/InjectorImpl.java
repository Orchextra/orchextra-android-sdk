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

package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.device.geolocation.geofencing.pendingintent.GeofenceIntentService;
import com.gigigo.orchextra.device.imagerecognition.ImageRecognitionReceiver;
import com.gigigo.orchextra.device.notificationpush.OrchextraGcmListenerService;
import com.gigigo.orchextra.di.components.DaggerGcmListenerServiceComponent;
import com.gigigo.orchextra.di.components.DaggerGeofenceIntentServiceComponent;
import com.gigigo.orchextra.di.components.DaggerOrchextraBootBroadcastReceiverComponent;
import com.gigigo.orchextra.di.components.DaggerOrchextraIrBroadcastReceiverComponent;
import com.gigigo.orchextra.di.components.DaggerServiceComponent;
import com.gigigo.orchextra.di.components.DaggerTaskServiceComponent;
import com.gigigo.orchextra.di.components.GcmListenerServiceComponent;
import com.gigigo.orchextra.di.components.GeofenceIntentServiceComponent;
import com.gigigo.orchextra.di.components.InteractorExecutionComponent;
import com.gigigo.orchextra.di.components.OrchextraBootBroadcastReceiverComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.components.OrchextraIrBroadcastReceiverComponent;
import com.gigigo.orchextra.di.components.ServiceComponent;
import com.gigigo.orchextra.di.components.TaskServiceComponent;
import com.gigigo.orchextra.di.modules.domain.InteractorsModule;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.sdk.background.OrchextraBootBroadcastReceiver;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.ui.scanner.OxScannerActivity;

import java.util.List;


public class InjectorImpl implements Injector {

    private OrchextraComponent orchextraComponent;

    public InjectorImpl(OrchextraComponent orchextraComponent) {
        this.orchextraComponent = orchextraComponent;
    }

    @Override
    public ServiceComponent injectServiceComponent(OrchextraBackgroundService myAppService) {

        ServiceComponent serviceComponent = DaggerServiceComponent.builder().
                orchextraComponent(orchextraComponent).build();
        serviceComponent.injectOrchextraService(myAppService);

        return serviceComponent;
    }

    @Override
    public TaskServiceComponent injectTaskServiceComponent(OrchextraGcmTaskService orchextraGcmTaskService) {

        TaskServiceComponent taskServiceComponent = DaggerTaskServiceComponent.builder().
                orchextraComponent(orchextraComponent).build();
        taskServiceComponent.injectTaskService(orchextraGcmTaskService);

        return taskServiceComponent;
    }

    @Override
    public GeofenceIntentServiceComponent injectGeofenceIntentServiceComponent(
            GeofenceIntentService geofenceIntentService) {

        GeofenceIntentServiceComponent gisc = DaggerGeofenceIntentServiceComponent.builder().
                orchextraComponent(orchextraComponent).build();
        gisc.injectGeofenceIntentService(geofenceIntentService);

        return gisc;
    }

    @Override
    public OrchextraBootBroadcastReceiverComponent injectBroadcastComponent(
            OrchextraBootBroadcastReceiver orchextraBootBroadcastReceiver) {

        OrchextraBootBroadcastReceiverComponent obbrc = DaggerOrchextraBootBroadcastReceiverComponent.builder().
                orchextraComponent(orchextraComponent).build();
        obbrc.injectOrchextraBootBroadcastReceiver(orchextraBootBroadcastReceiver);
        return obbrc;
    }

    private InteractorExecutionComponent createInteractorExecutionComponent() {
        return orchextraComponent.plus(new InteractorsModule());
    }

    @Override
    public InteractorExecutionComponent injectRegionsProviderInteractorExecution(InteractorExecution<List<OrchextraRegion>> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectRegionsProviderInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectSaveUserInteractorExecution(InteractorExecution<ClientAuthData> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectSaveUserInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectConfigInteractorInteractorExecution(InteractorExecution<OrchextraUpdates> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectConfigInteractorInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectBeaconEventsInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectBeaconEventsInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectGeofenceInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectGeofenceInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectGeofenceProviderInteractorExecution(
            InteractorExecution<List<OrchextraGeofence>> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectGeofenceProviderInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }



    @Override
    public InteractorExecutionComponent injectObtainIrCredentialsInteractorExecution(InteractorExecution<VuforiaCredentials> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectObtainIrCredentialsInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public InteractorExecutionComponent injectScannerInteractorExecution(InteractorExecution<BasicAction> interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectScannerInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }

    @Override
    public GcmListenerServiceComponent injectGcmListenerServiceComponent(OrchextraGcmListenerService orchextraGcmListenerService) {
        GcmListenerServiceComponent glsc = DaggerGcmListenerServiceComponent.builder().
                orchextraComponent(orchextraComponent).build();
        glsc.injectGcmListenerService(orchextraGcmListenerService);
        return glsc;
    }

    public OrchextraComponent getOrchextraComponent() {
        return orchextraComponent;
    }

    @Override
    public OrchextraComponent injectCodeScannerActivity(OxScannerActivity oxCodeScannerActivity) {
        orchextraComponent.injectCodeScannerActivity(oxCodeScannerActivity);
        return orchextraComponent;
    }

    @Override
    public OrchextraIrBroadcastReceiverComponent injectImageBroadcastComponent(
            ImageRecognitionReceiver imageRecognitionReceiver) {
        OrchextraIrBroadcastReceiverComponent oibrc = DaggerOrchextraIrBroadcastReceiverComponent.builder().
                orchextraComponent(orchextraComponent).build();
        oibrc.injectOrchextraIrBroadcastReceiver(imageRecognitionReceiver);
        return oibrc;
    }

    public InteractorExecutionComponent injectClearStorageInteractorExecution(InteractorExecution interactorExecution) {
        InteractorExecutionComponent interactorExecutionComponent = createInteractorExecutionComponent();
        interactorExecutionComponent.injectClearStorageInteractorExecution(interactorExecution);
        return interactorExecutionComponent;
    }
}
