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

package com.gigigo.orchextra.sdk.application;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.domain.abstractions.foreground.ForegroundTasksManager;
import com.gigigo.orchextra.sdk.OrchextraTasksManager;

public class ForegroundTasksManagerImpl implements ForegroundTasksManager {

  private final OrchextraTasksManager orchextraTasksManager;
  private final PermissionChecker permissionChecker;
  private final ContextProvider contextProvider;
  private final Permission permission;

  public ForegroundTasksManagerImpl(OrchextraTasksManager orchextraTasksManager,
      PermissionChecker permissionChecker, ContextProvider contextProvider,
      Permission permission1) {
    this.orchextraTasksManager = orchextraTasksManager;
    this.permissionChecker = permissionChecker;
    this.contextProvider = contextProvider;
    this.permission = permission1;// new PermissionLocationImp(null);
  }

  @Override public void startForegroundTasks() {
    boolean granted = permissionChecker.isGranted(permission);
    if (granted) {
      orchextraTasksManager.initForegroundTasks(granted);
    } else {
      // orchextraTasksManager.initForegroundTasks(false);
      //hotfix repsol
      if (contextProvider != null && contextProvider.getCurrentActivity() != null) {
        try {
          permissionChecker.askForPermission(permission, userPermissionRequestResponseListener,
              contextProvider.getCurrentActivity());
        } catch (Throwable tr) {
          orchextraTasksManager.initForegroundTasks(false);
        }
      } else {
        orchextraTasksManager.initForegroundTasks(false);
      }
    }
  }

  @Override public void finalizeForegroundTasks() {
    orchextraTasksManager.stopForegroundTasks();
  }

  private UserPermissionRequestResponseListener userPermissionRequestResponseListener =
      new UserPermissionRequestResponseListener() {
        @Override public void onPermissionAllowed(boolean permissionAllowed) {
          //if (permissionAllowed){
          orchextraTasksManager.initForegroundTasks(permissionAllowed);
          //}
        }
      };
}
