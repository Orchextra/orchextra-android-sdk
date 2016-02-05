package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.OrchextraPoint;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class RealmPointMapper implements RealmMapper<OrchextraPoint, RealmPoint> {

  @Override public RealmPoint modelToData(OrchextraPoint point) {
    RealmPoint realmPoint = new RealmPoint();
    realmPoint.setLat(point.getLat());
    realmPoint.setLng(point.getLng());
    return realmPoint;
  }

  @Override public OrchextraPoint dataToModel(RealmPoint realmPoint) {
    OrchextraPoint point = new OrchextraPoint();
    point.setLat(realmPoint.getLat());
    point.setLng(realmPoint.getLng());
    return point;
  }

}
