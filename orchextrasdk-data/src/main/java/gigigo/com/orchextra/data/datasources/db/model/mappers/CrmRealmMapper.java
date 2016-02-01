package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.entities.Crm;
import com.gigigo.orchextra.domain.entities.GenderType;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class CrmRealmMapper implements RealmMapper<Crm, CrmRealm>{

  private final KeyWordRealmMapper keyWordRealmMapper;

  public CrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public CrmRealm modelToData(Crm crm) {
    CrmRealm crmRealm = new CrmRealm();

    if (crm != null) {
      crmRealm.setKeywords(keyWordRealmMapper.stringKeyWordsToRealmList(crm.getKeywords()));
      crmRealm.setBirthDate(
              DateUtils.dateToStringWithFormat(crm.getBirthDate(), DateFormatConstants.DATE_FORMAT));
      crmRealm.setCrmId(crm.getCrmId());
      if (crm.getGender() != null) {
        crmRealm.setGender(crm.getGender().getStringValue());
      }
    }

    return crmRealm;
  }

  @Override public Crm dataToModel(CrmRealm crmRealm) {
    Crm crm = new Crm();

    if (crmRealm != null) {
      crm.setCrmId(crmRealm.getCrmId());
      crm.setKeywords(keyWordRealmMapper.realmKeyWordsToStringList(crmRealm.getKeywords()));
      try {
        crm.setGender(GenderType.valueOf(crmRealm.getGender()));
      } catch (IllegalArgumentException | NullPointerException e) {
      }
      crm.setBirthDate(DateUtils.stringToDateWithFormat(crmRealm.getBirthDate(),
              DateFormatConstants.DATE_FORMAT));
    }

    return crm;
  }


}
