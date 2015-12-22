package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.network.mappers.MapperUtils;
import gigigo.com.orchextra.data.datasources.db.model.KeyWordRealm;
import io.realm.RealmList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class KeyWordRealmMapper implements RealmMapper<String, KeyWordRealm>{

  @Override public KeyWordRealm modelToData(String s) {
    KeyWordRealm keyWordRealm = new KeyWordRealm(s);
    return keyWordRealm;
  }

  @Override public String dataToModel(KeyWordRealm keyWordRealm) {
    return keyWordRealm.getKeyword();
  }

  public RealmList<KeyWordRealm> stringKeyWordsToRealmList(List<String> keywords) {

    RealmList<KeyWordRealm> keyWordRealms = new RealmList<>();
    if (keyWordRealms == null ){
      return keyWordRealms;
    }

    for (String keyword:keywords){
      keyWordRealms.add(MapperUtils.checkNullDataRequest(this, keyword));
    }
    return keyWordRealms;
  }

  public List<String> realmKeyWordsToStringList(RealmList<KeyWordRealm> keyWordRealms) {

    List<String> keyWords = new ArrayList<>();
    if (keyWordRealms == null ){
      return keyWords;
    }

    for (KeyWordRealm keyWordRealm:keyWordRealms){
      keyWords.add(MapperUtils.checkNullDataResponse(this, keyWordRealm));
    }
    return keyWords;
  }
}
