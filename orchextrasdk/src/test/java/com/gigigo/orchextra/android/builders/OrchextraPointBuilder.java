package com.gigigo.orchextra.android.builders;

import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

public class OrchextraPointBuilder {

    public static final double LAT = 23.45;
    public static final double LNG = 45.21;

    private double lat = LAT;
    private double lng = LNG;

    public static OrchextraPointBuilder Builder() {
        return new OrchextraPointBuilder();
    }

    public OrchextraLocationPoint build() {
        OrchextraLocationPoint point = new OrchextraLocationPoint();
        point.setLat(lat);
        point.setLng(lng);
        return point;
    }
}
