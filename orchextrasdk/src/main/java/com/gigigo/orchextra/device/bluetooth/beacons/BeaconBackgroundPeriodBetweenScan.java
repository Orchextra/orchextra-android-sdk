package com.gigigo.orchextra.device.bluetooth.beacons;

public enum BeaconBackgroundPeriodBetweenScan {
    WEAK(600000L),
    LIGHT(300000L),
    MODERATE(120000L),
    STRONG(60000L),
    SEVERE(30000L),
    EXTREME(10000L);

    private final long intensity;

    BeaconBackgroundPeriodBetweenScan(long intensity) {
        this.intensity = intensity;
    }


    public long getIntensity() {
        return intensity;
    }
}
