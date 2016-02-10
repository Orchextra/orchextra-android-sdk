package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;
import gigigo.com.orchextra.data.datasources.db.model.RealmPoint;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class RealmPointMapper implements Mapper<OrchextraPoint, RealmPoint> {

  @Override public RealmPoint modelToExternalClass(OrchextraPoint point) {
    RealmPoint realmPoint = new RealmPoint();
    realmPoint.setLat(point.getLat());
    realmPoint.setLng(point.getLng());
    return realmPoint;
  }

  @Override public OrchextraPoint externalClassToModel(RealmPoint realmPoint) {
    OrchextraPoint point = new OrchextraPoint();
    point.setLat(realmPoint.getLat());
    point.setLng(realmPoint.getLng());
    return point;
  }

}
