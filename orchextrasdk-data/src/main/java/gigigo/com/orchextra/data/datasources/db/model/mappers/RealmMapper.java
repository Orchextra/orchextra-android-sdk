package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.ggglib.network.mappers.ResponseMapper;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface RealmMapper<Model,Data> extends RequestMapper<Model,Data>, ResponseMapper<Model,Data> {
}
