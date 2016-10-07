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

package gigigo.com.orchextra.data.datasources.api.service;

import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.responses.HttpResponse;
import com.gigigo.orchextra.domain.interactors.error.OrchextraBusinessErrors;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;


public class DefaultRetryOnErrorPolicyImpl
    implements RetryOnErrorPolicy<OrchextraApiErrorResponse> {

  /**
   * The aim of this method is implement the desired policy and implement a switch case strategy
   * that depending on the error Type (Http / Business or whatever) could allow us to decide if
   * we want to retry the request or not
   *
   * @param tries time the request has been already retried
   * @param error error description
   * @param httpResponse full http response of error
   */
  @Override public boolean shouldRetryWithErrorAndTries(int tries, OrchextraApiErrorResponse error,
      HttpResponse httpResponse) {
    return retryPolicy(tries, error.getCode());
  }

  @Override public boolean shouldRetryOnException(int tries, Exception e) {
    return defaultRetryPolicy(tries);
  }

  private boolean retryPolicy(int tries, int errorCode) {
    if (errorCode == OrchextraBusinessErrors.NO_AUTH_EXPIRED.getValue()
        || errorCode == OrchextraBusinessErrors.NO_AUTH_CREDENTIALS.getValue()
      //            || errorCode == OrchextraBusinessErrors.INTERNAL_SERVER_ERROR.getValue()
        ) {

      return false;
    } else {
      return defaultRetryPolicy(tries);
    }
  }

  private boolean defaultRetryPolicy(int tries) {
    return tries < 3;
  }
}
