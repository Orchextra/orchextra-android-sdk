package gigigo.com.orchextra.data.datasources.builders;

import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

public class PointRealmBuilder {

    public static final double LAT = 23.45;
    public static final double LNG = 45.21;

    private double lat = LAT;
    private double lng = LNG;

    public static PointRealmBuilder Builder() {
        return new PointRealmBuilder();
    }

    public RealmPoint build() {
        RealmPoint point = new RealmPoint();
        point.setLat(lat);
        point.setLng(lng);
        return point;
    }
}
