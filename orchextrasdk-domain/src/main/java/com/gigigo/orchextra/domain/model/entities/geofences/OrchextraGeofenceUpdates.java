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

package com.gigigo.orchextra.domain.model.entities.geofences;

import com.gigigo.orchextra.domain.model.entities.Updates;

import java.util.List;

public class OrchextraGeofenceUpdates implements Updates {

    List<OrchextraGeofence> newGeofences;
    List<OrchextraGeofence> deleteGeofences;

    public OrchextraGeofenceUpdates(List<OrchextraGeofence> newGeofences,
                                    List<OrchextraGeofence> deleteGeofences) {
        setNewGeofences(newGeofences);
        setDeleteGeofences(deleteGeofences);
    }

    public List<OrchextraGeofence> getNewGeofences() {
        return newGeofences;
    }

    public void setNewGeofences(List<OrchextraGeofence> newGeofences) {
        this.newGeofences = newGeofences;
    }

    public List<OrchextraGeofence> getDeleteGeofences() {
        return deleteGeofences;
    }

    public void setDeleteGeofences(List<OrchextraGeofence> deleteGeofences) {
        this.deleteGeofences = deleteGeofences;
    }

    @Override
    public boolean hasChanges() {
        return newGeofences.size() > 0 || deleteGeofences.size() > 0;
    }
}
