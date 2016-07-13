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

import com.gigigo.orchextra.domain.model.entities.Updates;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofenceUpdates;

public class OrchextraUpdates implements Updates {

    private OrchextraRegionUpdates orchextraRegionUpdates;
    private OrchextraGeofenceUpdates orchextraGeofenceUpdates;
    private VuforiaCredentials vuforiaCredentialsUpdates;


    @Deprecated
    public OrchextraUpdates(OrchextraRegionUpdates orchextraRegionUpdates,
                             OrchextraGeofenceUpdates orchextraGeofenceChanges,
                            VuforiaCredentials vuforiaCredentialsChanges ) {

        setOrchextraRegionUpdates(orchextraRegionUpdates);
        setOrchextraGeofenceUpdates(orchextraGeofenceChanges);
        setVuforiaCredentialsUpdates(vuforiaCredentialsChanges);

    }

    public OrchextraUpdates() {

    }

    public OrchextraRegionUpdates getOrchextraRegionUpdates() {
        return orchextraRegionUpdates;
    }

    public void setOrchextraRegionUpdates(OrchextraRegionUpdates orchextraRegionUpdates) {
        this.orchextraRegionUpdates = orchextraRegionUpdates;
    }

    public  OrchextraGeofenceUpdates getOrchextraGeofenceUpdates() {
        return orchextraGeofenceUpdates;
    }

    public void setOrchextraGeofenceUpdates(OrchextraGeofenceUpdates orchextraGeofenceUpdates) {
        this.orchextraGeofenceUpdates = orchextraGeofenceUpdates;
    }

    public VuforiaCredentials getVuforiaCredentialsUpdates() {
        return vuforiaCredentialsUpdates;
    }

    public void setVuforiaCredentialsUpdates(VuforiaCredentials vuforiaCredentialsUpdates) {
        this.vuforiaCredentialsUpdates = vuforiaCredentialsUpdates;
    }


    @Override
    public boolean hasChanges() {
        return (orchextraRegionUpdates != null && orchextraRegionUpdates.hasChanges())
                || (orchextraGeofenceUpdates != null && orchextraGeofenceUpdates.hasChanges())
                || vuforiaCredentialsUpdates != null
                 ;
    }
}
