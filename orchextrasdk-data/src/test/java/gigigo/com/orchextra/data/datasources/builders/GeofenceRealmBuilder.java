package gigigo.com.orchextra.data.datasources.builders;

import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import com.gigigo.orchextra.domain.model.ProximityItemType;

import java.util.Calendar;
import java.util.Date;

import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.KeyWordRealm;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;
import io.realm.RealmList;

public class GeofenceRealmBuilder {

    public static final OrchextraPoint POINT = PointBuilder.Builder().build();
    public static final int RADIUS = 30;
    public static final String ID = "1234";
    public static final String CODE = "999";
    public static final String NAME = "GEOFENCE";
    public static final String TYPE = ProximityItemType.GEOFENCE.getStringValue();
    public static final int STAY = 3000;
    public static final Date CREATED = DateBuilder.getCalendar(2013, Calendar.SEPTEMBER, 29, 18, 46, 19);
    public static final Date UPDATED = DateBuilder.getCalendar(2014, Calendar.SEPTEMBER, 29, 18, 46, 19);
    @Deprecated
    public static final String TAG_NAME = "tagName";

    public static final String CREATEDS = "2013-09-29T18:46:19Z";
    public static final String UPDATEDS = "2014-09-29T18:46:19Z";

    private RealmPoint point = PointRealmBuilder.Builder().build();
    private int radius = RADIUS;
    private String id = ID;
    private String code = CODE;
    private String name = NAME;
    private String type = TYPE;
    private int stay = STAY;
    private String created = CREATEDS;
    private String updated = UPDATEDS;
    @Deprecated
    private String tag = TAG_NAME;

    public static GeofenceRealmBuilder Builder() {
        return new GeofenceRealmBuilder();
    }

    public GeofenceRealm build() {
        GeofenceRealm geofence = new GeofenceRealm();
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

        RealmList<KeyWordRealm> list = new RealmList<>();
        KeyWordRealm keyWordRealm = new KeyWordRealm();
        keyWordRealm.setKeyword(tag);
        list.add(keyWordRealm);

        geofence.setTags(list);

        return geofence;
    }
}
