package gigigo.com.orchextrasdk;

import android.app.Application;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.OrchextraCompletionCallback;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/3/16.
 */
public class App extends Application implements OrchextraCompletionCallback {
  @Override public void onCreate() {
    super.onCreate();
    Orchextra.init(this, this);
    Orchextra.start("937e89efec8f0b2a65b4e4cdc9aa587fc5737042", "7ad0db1df543e7fea9b36c074a11a16447946423");
  }

  @Override public void onSuccess() {

  }

  @Override public void onError(String s) {

  }

  @Override public void onInit(String s) {

  }
}
