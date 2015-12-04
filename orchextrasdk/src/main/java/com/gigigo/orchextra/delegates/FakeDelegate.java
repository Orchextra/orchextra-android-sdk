package com.gigigo.orchextra.delegates;

import android.content.Context;
import android.os.Handler;
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
public class FakeDelegate implements Delegate{

  @Inject Context appContext;
  private static FakeDelegate me;
  private DelegateComponent delegateComponent;

  public FakeDelegate() {
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
    Handler handler = new Handler(appContext.getMainLooper());
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        Toast.makeText(appContext, " HEEEEYYYYYYYYYYY FakeDelegate", Toast.LENGTH_LONG);
        GGGLogImpl.log("FakeDelegate is saying hello to " + appContext.getString(
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
