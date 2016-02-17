package gigigo.com.orchextra.data.datasources.api.service;

import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.responses.HttpResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 30/11/15.
 */
public class DefaultRetryOnErrorPolicyImpl implements
    RetryOnErrorPolicy<OrchextraApiErrorResponse> {

  /**
   * The aim of this method is implement the desired policy and implement a switch case strategy
   * that depending on the error Type (Http / Business or whatever) could allow us to decide if
   * we want to retry the request or not
   *
   * @param tries time the request has been already retried
   * @param error error description
   * @param httpResponse full http response of error
   * @return
   */
  @Override public boolean shouldRetryWithErrorAndTries(int tries, OrchextraApiErrorResponse error,
      HttpResponse httpResponse) {
    if (tries<3){
      return true;
    }else {
      return false;
    }
  }

  @Override public boolean shouldRetryOnException(int tries, Exception e) {
    if (tries<3){
      return true;
    }else {
      return false;
    }
  }
}
