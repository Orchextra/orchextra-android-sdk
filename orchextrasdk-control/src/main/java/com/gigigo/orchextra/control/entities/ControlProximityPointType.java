package com.gigigo.orchextra.control.entities;

public enum ControlProximityPointType {
    BEACON("beacon"),
    GEOFENCE("geofence");

    private final String text;

    ControlProximityPointType(final String text) {
        this.text = text;
    }

    public String getStringValue() {
        return text;
    }

    public static ControlProximityPointType getProximityPointTypeValue(String type) {
        if (type != null) {
            if (type.equals(BEACON.getStringValue())) {
                return BEACON;
            } else {
                return GEOFENCE;
            }
        } else {
            return null;
        }
    }
}
