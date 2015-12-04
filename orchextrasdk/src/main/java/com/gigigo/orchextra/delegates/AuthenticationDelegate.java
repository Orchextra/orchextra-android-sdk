package com.gigigo.orchextra.delegates;

import android.content.Context;
import android.widget.Toast;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.di.components.DelegateComponent;
import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class AuthenticationDelegate implements Delegate{

  @Inject Context appContext;
  private static AuthenticationDelegate me;
  private DelegateComponent delegateComponent;

  public AuthenticationDelegate() {
    init();
  }

  @Override public void init() {
    delegateComponent = Orchextra.getInjector().injectAuthDelegate(this);
  }

  @Override public void destroy() {
    delegateComponent = null;
    me = null;
  }

  public static void authenticate (String apiKey, String apiSecret){
    getInstace().authenticateTask(apiKey, apiSecret);
  }

  private void authenticateTask(String apiKey, String apiSecret) {
    GGGLogImpl.log(
        "You are Being Authenticated and you app name is" + appContext.getString(R.string.app_name));
  }

  private static AuthenticationDelegate getInstace(){
    if ( me != null ){
      return me;
    }else{
      return new AuthenticationDelegate();
    }
  }

}
