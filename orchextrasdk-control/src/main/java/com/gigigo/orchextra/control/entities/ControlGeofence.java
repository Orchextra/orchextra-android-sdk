package com.gigigo.orchextra.control.entities;

public class ControlGeofence extends ControlProximityPoint {

    private ControlPoint point;
    private int radius;

    public ControlPoint getPoint() {
        return point;
    }

    public void setPoint(ControlPoint point) {
        this.point = point;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
