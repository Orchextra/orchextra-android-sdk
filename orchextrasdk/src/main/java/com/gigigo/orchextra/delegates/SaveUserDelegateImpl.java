/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.delegates;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.control.controllers.authentication.SaveUserController;
import com.gigigo.orchextra.control.controllers.authentication.SaveUserDelegate;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;


public class SaveUserDelegateImpl implements SaveUserDelegate {

  private final SaveUserController saveUserController;

  public SaveUserDelegateImpl(SaveUserController saveUserController) {
    this.saveUserController = saveUserController;
  }

  @Override public void init() {
    saveUserController.attachView(this);
  }

  @Override public void destroy() {
    saveUserController.detachView();
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
