package com.gigigo.orchextra.control.entities;

public class GeofenceControl extends ProximityPointControl {

    private PointControl point;
    private int radius;

    public PointControl getPoint() {
        return point;
    }

    public void setPoint(PointControl point) {
        this.point = point;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
