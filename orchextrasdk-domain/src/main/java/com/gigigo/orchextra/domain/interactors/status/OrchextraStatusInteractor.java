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

package com.gigigo.orchextra.domain.interactors.status;

import com.gigigo.orchextra.domain.model.vo.OrchextraStatus;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.services.status.LoadOrchextraServiceStatus;
import com.gigigo.orchextra.domain.services.status.UpdateOrchextraServiceStatus;

public class OrchextraStatusInteractor
    implements Interactor<InteractorResponse<OrchextraStatus>>{

  //private final LoadOrchextraServiceStatus loadOrchextraServiceStatus;
  //private final UpdateOrchextraServiceStatus updateOrchextraServiceStatus;

  private StatusOperationType statusOperationType;
  private OrchextraStatus orchextraStatus;

  //public OrchextraStatusInteractor(LoadOrchextraServiceStatus loadOrchextraServiceStatus,
  //    UpdateOrchextraServiceStatus updateOrchextraServiceStatus) {
  //  this.loadOrchextraServiceStatus = loadOrchextraServiceStatus;
  //  this.updateOrchextraServiceStatus = updateOrchextraServiceStatus;
  //}

  @Override public InteractorResponse<OrchextraStatus> call() throws Exception {
    //if (statusOperationType == StatusOperationType.LOAD_ORCHEXTRA_STATUS){
    //  return loadOrchextraServiceStatus.load();
    //}else{
    //  return updateOrchextraServiceStatus.update(orchextraStatus);
    //}
    return null;
  }

  public void updateData(OrchextraStatus orchextraStatus){
    this.statusOperationType = StatusOperationType.UPDATE_ORCHEXTRA_STATUS;
    this.orchextraStatus = orchextraStatus;
  }

  public void loadData() {
    this.statusOperationType = StatusOperationType.LOAD_ORCHEXTRA_STATUS;
  }
}
