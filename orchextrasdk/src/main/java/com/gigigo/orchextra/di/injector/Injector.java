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
import com.gigigo.orchextra.di.components.GeofenceIntentServiceComponent;
import com.gigigo.orchextra.di.components.InteractorExecutionComponent;
import com.gigigo.orchextra.di.components.OrchextraBootBroadcastReceiverComponent;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.sdk.background.OrchextraBootBroadcastReceiver;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.di.components.ServiceComponent;
import com.gigigo.orchextra.di.components.TaskServiceComponent;
import java.util.List;


public interface Injector {
  ServiceComponent injectServiceComponent (OrchextraBackgroundService myAppService);
  TaskServiceComponent injectTaskServiceComponent(OrchextraGcmTaskService orchextraGcmTaskService);
  GeofenceIntentServiceComponent injectGeofenceIntentServiceComponent(GeofenceIntentService geofenceIntentService);
  OrchextraBootBroadcastReceiverComponent injectBroadcastComponent(OrchextraBootBroadcastReceiver orchextraBootBroadcastReceiver);
  InteractorExecutionComponent injectRegionsProviderInteractorExecution(InteractorExecution<List<OrchextraRegion>> interactorExecution);
  InteractorExecutionComponent injectSaveUserInteractorExecution(InteractorExecution<ClientAuthData> interactorExecution);
  InteractorExecutionComponent injectConfigInteractorInteractorExecution(InteractorExecution<OrchextraUpdates> interactorExecution);
  InteractorExecutionComponent injectBeaconEventsInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);
  InteractorExecutionComponent injectGeofenceInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);
}
