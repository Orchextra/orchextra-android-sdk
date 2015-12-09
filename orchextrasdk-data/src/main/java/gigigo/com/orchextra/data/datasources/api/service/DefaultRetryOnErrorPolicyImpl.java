package gigigo.com.orchextra.data.datasources.api.service;

import com.gigigo.ggglib.network.defaultelements.RetryOnErrorPolicy;
import com.gigigo.ggglib.network.responses.HttpResponse;
import gigigo.com.orchextra.data.datasources.api.model.responses.OrchextraApiErrorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 30/11/15.
 */
public class DefaultRetryOnErrorPolicyImpl implements
    RetryOnErrorPolicy<OrchextraApiErrorResponse> {

  @Override public boolean shouldRetryWithErrorAndTries(int tries, OrchextraApiErrorResponse error,
      HttpResponse httpResponse) {
    //TODO implement policy Switch Http and Business responses
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
