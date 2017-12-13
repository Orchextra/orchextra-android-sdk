/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo.utils.integration;

import android.app.Application;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraOptions;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.OrchextraTokenReceiver;
import com.gigigo.orchextra.core.domain.actions.actionexecutors.customaction.OrchextraCustomActionListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.geofence.OxGeofenceImp;
import com.gigigo.orchextra.indoorpositioning.OxIndoorPositioningImp;
import com.gigigo.orchextrasdk.demo.ui.settings.SettingsActivity;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Ox3ManagerImp implements OxManager {

  Orchextra orchextra;

  public Ox3ManagerImp() {
    this.orchextra = Orchextra.INSTANCE;
  }

  @Override public void startImageRecognition() {
    orchextra.openImageRecognition();
  }

  @Override public void startScanner() {
    orchextra.openScanner();
  }

  @Override public void init(final Application application, Config config,
      final StatusListener statusListener) {

    orchextra.setStatusListener(new OrchextraStatusListener() {
      @Override public void onStatusChange(boolean isReady) {
        if (isReady) {
          orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(application));
          orchextra.getTriggerManager()
              .setIndoorPositioning(OxIndoorPositioningImp.Factory.create(application));

          orchextra.setNotificationActivityClass(SettingsActivity.class);

          statusListener.isReady();
        } else {
          statusListener.onError("SDK isn't ready");
        }
      }
    });

    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NotNull Error error) {
        statusListener.onError(error.getMessage());
      }
    });

    OrchextraOptions options =
        new OrchextraOptions.Builder().firebaseApiKey(config.getFirebaseApiKey())
            .firebaseApplicationId(config.getFirebaseApplicationId())
            .debuggable(true)
            .build();

    orchextra.init(application, config.getApiKey(), config.getApiSecret(), options);
    orchextra.setScanTime(30);
  }

  @Override public void finish() {
    orchextra.finish();
  }

  @Override public void removeListeners() {
    if (orchextra != null) {
      orchextra.removeStatusListener();
      orchextra.removeErrorListener();
    }
  }

  @Override public Boolean isReady() {
    return orchextra.isReady();
  }

  @Override public void getToken(final TokenReceiver tokenReceiver) {
    orchextra.getToken(new OrchextraTokenReceiver() {
      @Override public void onGetToken(@NotNull String oxToken) {
        tokenReceiver.onGetToken(oxToken);
      }
    });
  }

  @Override public void setErrorListener(final ErrorListener errorListener) {
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NotNull Error error) {
        errorListener.onError(error.getMessage());
      }
    });
  }

  @Override public void setBusinessUnits(List<String> businessUnits) {
    orchextra.getCrmManager().setDeviceData(null, businessUnits);
  }

  @Override public void setCustomSchemeReceiver(final CustomActionListener customSchemeReceiver) {
    orchextra.setCustomActionListener(new OrchextraCustomActionListener() {
      @Override public void onCustomSchema(@NotNull String customSchema) {
        customSchemeReceiver.onCustomSchema(customSchema);
      }
    });
  }
}
