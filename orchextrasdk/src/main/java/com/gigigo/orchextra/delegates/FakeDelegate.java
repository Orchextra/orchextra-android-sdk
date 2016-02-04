package com.gigigo.orchextra.delegates;

import android.os.Handler;
import android.widget.Toast;
import com.gigigo.ggglib.ContextProvider;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.control.controllers.base.Delegate;
import com.gigigo.orchextra.di.components.DelegateComponent;
import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class FakeDelegate implements Delegate {

  @Inject ContextProvider contextProvider;
  private static FakeDelegate me;
  private DelegateComponent delegateComponent;

  private FakeDelegate() {
    init();
  }

  @Override public void init() {
    delegateComponent = Orchextra.getInjector().injectFakeDelegate(this);
  }

  @Override public void destroy() {
    delegateComponent = null;
    me = null;
  }

  private void showToastTask() {
    Handler handler = new Handler(contextProvider.getApplicationContext().getMainLooper());
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        Toast.makeText(contextProvider.getApplicationContext(), " HEEEEYYYYYYYYYYY FakeDelegate", Toast.LENGTH_LONG);
        GGGLogImpl.log("FakeDelegate is saying hello to " +
            contextProvider.getApplicationContext().getString(
            R.string.app_name));

      }
    }, 3000);
  }

  public static void showToast() {
    getInstace().showToastTask();
  }

  private static FakeDelegate getInstace(){
    if ( me != null ){
      return me;
    }else{
      return new FakeDelegate();
    }
  }

}
