package com.gigigo.orchextra.delegates;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.control.controllers.authentication.SaveUserController;
import com.gigigo.orchextra.control.controllers.authentication.SaveUserDelegate;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class SaveUserDelegateImpl implements SaveUserDelegate {

  private final SaveUserController saveUserController;

  public SaveUserDelegateImpl(SaveUserController saveUserController) {
    this.saveUserController = saveUserController;
  }

  @Override public void init() {
    saveUserController.attachDelegate(this);
  }

  @Override public void destroy() {
    saveUserController.detachDelegate();
  }

  @Override public void saveUserSuccessful() {
    destroy();
  }

  @Override public void saveUserError() {
    GGGLogImpl.log("Save user was not successful", LogLevel.ERROR);
    destroy();
  }

  @Override
  public void saveUser(Crm crm) {
    init();
    saveUserController.saveUser(crm);
  }

}
