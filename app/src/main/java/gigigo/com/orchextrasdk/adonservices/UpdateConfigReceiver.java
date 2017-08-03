package gigigo.com.orchextrasdk.adonservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by nubor on 16/11/2016.
 */
public class UpdateConfigReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    Log.e("UpdateConfigReceiver", "UpdateConfigReceiver this is not and error, red color is c00l");
    if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
      UpdateConfigUtility updater = new UpdateConfigUtility(context);
      updater.createUpdateConfigurationByTime(60 * 1000 * 3);
      Log.e("UpdateConfigReceiver", "Setting alarm");
    } else {
      if (UpdateConfigUtility.ACTION_REFRESH_CONFIG.equals(intent.getAction())) {
        Log.e("UpdateConfigReceiver", "RefreshConfigurationInBackground");
        //Orchextra.refreshConfigurationInBackground(context);
      } else {
        Log.e("UpdateConfigReceiver", "do nothing with ACTION->" + intent.getAction());
      }
    }
  }
}
