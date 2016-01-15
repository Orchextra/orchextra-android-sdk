package com.gigigo.orchextra.builders;

import com.gigigo.orchextra.domain.entities.Point;

public class PointBuilder {

    public static final double LAT = 23.45;
    public static final double LNG = 45.21;

    private double lat = LAT;
    private double lng = LNG;

    public static PointBuilder Builder() {
        return new PointBuilder();
    }

    public PointBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public PointBuilder setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public Point build() {
        Point point = new Point();
        point.setLat(lat);
        point.setLng(lng);
        return point;
    }


}
