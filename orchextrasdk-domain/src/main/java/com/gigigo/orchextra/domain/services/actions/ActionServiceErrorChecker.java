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

package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.services.auth.AuthenticationService;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class ActionServiceErrorChecker extends ServiceErrorChecker {

  public ActionServiceErrorChecker(AuthenticationService authenticationService) {
    super(authenticationService);
  }

  @Override protected InteractorResponse checkConcreteException(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }

  @Override protected InteractorResponse checkConcreteBusinessErrors(BusinessError businessError) {
    return new InteractorResponse(new GenericError(businessError));
  }
}
