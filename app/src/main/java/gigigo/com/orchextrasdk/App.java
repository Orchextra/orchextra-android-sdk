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
    Orchextra.start("key", "secret");
  }

  @Override public void onSuccess() {

  }

  @Override public void onError(String s) {

  }

  @Override public void onInit(String s) {

  }
}
