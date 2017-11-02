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

package com.gigigo.orchextra.sdk.scanner;

import android.app.Activity;
import android.content.Intent;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.ggglib.permissions.UserPermissionRequestResponseListener;
import com.gigigo.orchextra.device.permissions.PermissionCameraImp;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.ui.scanner.OxScannerActivity;
import orchextra.javax.inject.Inject;

public class ScannerManager {

  private final ContextProvider context;
  @Inject OrchextraLogger orchextraLogger;
  PermissionChecker permissionChecker;
  PermissionCameraImp cameraPermissionImp;

  public ScannerManager(ContextProvider context, PermissionChecker permissionChecker1,
      PermissionCameraImp cameraPermissionImp1) {
    this.context = context;
    this.permissionChecker = permissionChecker1;
    this.cameraPermissionImp = cameraPermissionImp1;
  }

  public void open() {
    initScannerCamera();
  }

  private void initScannerCamera() {
    checkCameraPermission();
  }

  private void checkCameraPermission() {
    boolean isGranted = permissionChecker.isGranted(cameraPermissionImp);
    if (!isGranted) {
      permissionChecker.askForPermission(cameraPermissionImp, cameraPermissionResponseListener,
          (Activity) context.getCurrentActivity());
    } else {
      openActivity();
    }
  }

  private UserPermissionRequestResponseListener cameraPermissionResponseListener =
      new UserPermissionRequestResponseListener() {
        @Override public void onPermissionAllowed(boolean permissionAllowed) {
          if (permissionAllowed) {
            openActivity();
          }
        }
      };

  private void openActivity() {
    Intent intent = new Intent(context.getApplicationContext(), OxScannerActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    context.getApplicationContext().startActivity(intent);
  }
}
