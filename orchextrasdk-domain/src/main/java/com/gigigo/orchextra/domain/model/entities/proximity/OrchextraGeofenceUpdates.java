package com.gigigo.orchextra.domain.model.entities.proximity;

import java.util.List;

public class OrchextraGeofenceUpdates {

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

  public boolean hasChanges() {
    return newGeofences.size() > 0 || deleteGeofences.size() > 0;
  }
}
