package com.gigigo.orchextra.android.builders;

import com.gigigo.orchextra.control.entities.ControlPoint;

public class ControlPointBuilder {

    public static final double LAT = 23.45;
    public static final double LNG = 45.21;

    private double lat = LAT;
    private double lng = LNG;

    public static ControlPointBuilder Builder() {
        return new ControlPointBuilder();
    }

    public ControlPoint build() {
        ControlPoint point = new ControlPoint();
        point.setLat(lat);
        point.setLng(lng);
        return point;
    }
}
