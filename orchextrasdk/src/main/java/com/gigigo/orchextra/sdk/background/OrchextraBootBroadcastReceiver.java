package com.gigigo.orchextra.sdk.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 29/1/16.
 */
public class OrchextraBootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
      if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
        Intent pushIntent = new Intent(context, OrchextraBackgroundService.class);
        context.startService(pushIntent);
      }
    }
}
