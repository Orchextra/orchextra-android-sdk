package com.gigigo.orchextra.android.builders;

import com.gigigo.orchextra.domain.model.ProximityItemType;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.vo.OrchextraLocationPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ControlGeofenceBuilder {

    public static final OrchextraLocationPoint POINT = OrchextraPointBuilder.Builder().build();
    public static final int RADIUS = 30;
    public static final String ID = "1234";
    public static final String CODE = "999";
    public static final String NAME = "GEOFENCE";
    public static final ProximityItemType TYPE = ProximityItemType.GEOFENCE;
    public static final int STAY = 3000;
    public static final Date CREATED = DateBuilder.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);
    public static final Date UPDATED = DateBuilder.getCalendar(2014, Calendar.SEPTEMBER, 29, 18, 46, 19);
    @Deprecated
    public static final String TAG_NAME = "tagName";

    public static final String CREATEDS = "2013-09-29T18:46:19Z";
    public static final String UPDATEDS = "2014-09-29T18:46:19Z";


    private OrchextraLocationPoint point = POINT;
    private int radius = RADIUS;
    private String id = ID;
    private String code = CODE;
    private String name = NAME;
    private ProximityItemType type = TYPE;
    private int stay = STAY;
    private Date created = CREATED;
    private Date updated = UPDATED;
    @Deprecated
    private String tag = TAG_NAME;

    public static ControlGeofenceBuilder Builder() {
        return new ControlGeofenceBuilder();
    }

    public OrchextraGeofence build() {
        OrchextraGeofence geofence = new OrchextraGeofence();
        geofence.setPoint(point);
        geofence.setRadius(radius);
        geofence.setId(id);
        geofence.setCode(code);
        geofence.setName(name);
        geofence.setType(type);
        geofence.setNotifyOnEntry(true);
        geofence.setNotifyOnExit(true);
        geofence.setCreatedAt(created);
        geofence.setUpdatedAt(updated);
        geofence.setStayTime(stay);

        List<String> list = new ArrayList<>();
        list.add(tag);

        geofence.setTags(list);

        return geofence;
    }
}
