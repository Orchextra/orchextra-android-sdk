package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.GenderType;
import com.gigigo.orchextra.domain.model.config.Config;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;

import java.util.Date;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class SaveUserInfoInteractor implements Interactor<InteractorResponse<Boolean>> {

  private final ConfigDataProvider configDataProvider;
  private Crm user;

  public SaveUserInfoInteractor(ConfigDataProvider configDataProvider) {
    this.configDataProvider = configDataProvider;
  }

  public void setUserData(String id, GenderType gender, Date birthDate, List<String> keyWords){
    this.user = new Crm(id, gender, birthDate, keyWords);
  }

  @Override public InteractorResponse<Boolean> call() throws Exception {
    //TODO Is this interactor needed??
    Config config = new Config(user);
//    BusinessObject<ConfigInfoResult> bo = configDataProvider.sendConfigInfo(config);
    //TODO manage Errors
    return new InteractorResponse<>(new Boolean(true));
  }

}
