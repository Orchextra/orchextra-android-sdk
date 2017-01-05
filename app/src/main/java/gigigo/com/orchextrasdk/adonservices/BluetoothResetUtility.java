package gigigo.com.orchextrasdk.adonservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by nubor on 15/12/2016.
 */
public class BluetoothResetUtility {

    Context mContext;
    private AlarmManager alarm;
    public final static String ACTION_RESET_BT = "android.intent.action.RESET_BT";

    public BluetoothResetUtility(Context mContext) {
        this.mContext = mContext;
        alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void createAlarmResetBluetoothEachTime(long time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Intent intent = new Intent(mContext, BluetoothResetReceiver.class);
            intent.setAction(ACTION_RESET_BT);
            PendingIntent broadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, broadCastPending);
            mContext.startService(intent);
        }

    }
}
