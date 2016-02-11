package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.entities.Vuforia;
import com.gigigo.orchextra.domain.model.vo.Theme;

public class OrchextraUpdates {

    private OrchextraBeaconUpdates orchextraBeaconUpdates;
    private OrchextraGeofenceUpdates orchextraGeofenceUpdates;
    private Vuforia vuforiaUpdates;
    private Theme themeUpdates;

    public OrchextraUpdates(OrchextraBeaconUpdates orchextraBeaconUpdates,
                            OrchextraGeofenceUpdates orchextraGeofenceChanges,
                            Vuforia vuforiaChanges,
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
}
