package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Point;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class RealmPointMapper implements RealmMapper<Point, RealmPoint> {

  @Override public RealmPoint modelToData(Point point) {
    RealmPoint realmPoint = new RealmPoint();
    realmPoint.setLat(point.getLat());
    realmPoint.setLng(point.getLng());
    return realmPoint;
  }

  @Override public Point dataToModel(RealmPoint realmPoint) {
    Point point = new Point();
    point.setLat(realmPoint.getLat());
    point.setLng(realmPoint.getLng());
    return point;
  }

}
