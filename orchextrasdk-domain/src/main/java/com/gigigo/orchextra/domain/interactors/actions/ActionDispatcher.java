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

package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.model.actions.strategy.OrchextraNotification;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;


public interface ActionDispatcher {

  void dispatchAction(BrowserAction action);

  void dispatchAction(BrowserAction action, OrchextraNotification notification);

  void dispatchAction(WebViewAction action);

  void dispatchAction(WebViewAction action, OrchextraNotification notification);

  void dispatchAction(CustomAction action);

  void dispatchAction(CustomAction action, OrchextraNotification notification);

  void dispatchAction(ScanAction action);

  void dispatchAction(ScanAction action, OrchextraNotification notification);

  void dispatchAction(VuforiaScanAction action);

  void dispatchAction(VuforiaScanAction action, OrchextraNotification notification);

  void dispatchAction(NotificationAction action);

  void dispatchAction(NotificationAction action, OrchextraNotification notification);

  void dispatchAction(EmptyAction action);

  void dispatchAction(EmptyAction action, OrchextraNotification notification);
}
