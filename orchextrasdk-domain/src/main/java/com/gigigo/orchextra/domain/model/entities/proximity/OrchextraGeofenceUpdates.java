package com.gigigo.orchextra.domain.model.entities.proximity;

import java.util.List;

public class OrchextraGeofenceUpdates {

    List<OrchextraGeofence> newGeofences;
    List<OrchextraGeofence> updateGeofences;
    List<OrchextraGeofence> deleteGeofences;

    public OrchextraGeofenceUpdates(List<OrchextraGeofence> newGeofences, List<OrchextraGeofence> updateGeofences, List<OrchextraGeofence> deleteGeofences) {
        setNewGeofences(newGeofences);
        setUpdateGeofences(updateGeofences);
        setDeleteGeofences(deleteGeofences);
    }

    public List<OrchextraGeofence> getNewGeofences() {
        return newGeofences;
    }

    public void setNewGeofences(List<OrchextraGeofence> newGeofences) {
        this.newGeofences = newGeofences;
    }

    public List<OrchextraGeofence> getUpdateGeofences() {
        return updateGeofences;
    }

    public void setUpdateGeofences(List<OrchextraGeofence> updateGeofences) {
        this.updateGeofences = updateGeofences;
    }

    public List<OrchextraGeofence> getDeleteGeofences() {
        return deleteGeofences;
    }

    public void setDeleteGeofences(List<OrchextraGeofence> deleteGeofences) {
        this.deleteGeofences = deleteGeofences;
    }

    public boolean hasChanges() {
        return newGeofences.size() > 0 ||
                updateGeofences.size() > 0 ||
                deleteGeofences.size() > 0;
    }
}
