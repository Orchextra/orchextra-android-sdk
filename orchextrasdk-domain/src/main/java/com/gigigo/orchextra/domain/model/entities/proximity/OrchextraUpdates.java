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

package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.vo.Theme;

public class OrchextraUpdates {

  private OrchextraBeaconUpdates orchextraBeaconUpdates;
  private OrchextraGeofenceUpdates orchextraGeofenceUpdates;
  private Vuforia vuforiaUpdates;
  private Theme themeUpdates;

  public OrchextraUpdates(OrchextraBeaconUpdates orchextraBeaconUpdates,
      OrchextraGeofenceUpdates orchextraGeofenceChanges, Vuforia vuforiaChanges,
      Theme themeChanges) {

    setOrchextraBeaconUpdates(orchextraBeaconUpdates);
    setOrchextraGeofenceUpdates(orchextraGeofenceChanges);
    setVuforiaUpdates(vuforiaChanges);
    setThemeUpdates(themeChanges);
  }

  public OrchextraBeaconUpdates getOrchextraBeaconUpdates() {
    return orchextraBeaconUpdates;
  }

  public void setOrchextraBeaconUpdates(OrchextraBeaconUpdates orchextraBeaconUpdates) {
    this.orchextraBeaconUpdates = orchextraBeaconUpdates;
  }

  public OrchextraGeofenceUpdates getOrchextraGeofenceUpdates() {
    return orchextraGeofenceUpdates;
  }

  public void setOrchextraGeofenceUpdates(OrchextraGeofenceUpdates orchextraGeofenceUpdates) {
    this.orchextraGeofenceUpdates = orchextraGeofenceUpdates;
  }

  public Vuforia getVuforiaUpdates() {
    return vuforiaUpdates;
  }

  public void setVuforiaUpdates(Vuforia vuforiaUpdates) {
    this.vuforiaUpdates = vuforiaUpdates;
  }

  public Theme getThemeUpdates() {
    return themeUpdates;
  }

  public void setThemeUpdates(Theme themeUpdates) {
    this.themeUpdates = themeUpdates;
  }

  public boolean hasChanges() {
    return orchextraBeaconUpdates.hasChanges()
        || orchextraGeofenceUpdates.hasChanges()
        || vuforiaUpdates != null
        || themeUpdates != null;
  }
}
