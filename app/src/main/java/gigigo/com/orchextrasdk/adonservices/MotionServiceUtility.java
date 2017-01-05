package gigigo.com.orchextrasdk.adonservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by Alberto on 16/03/2016.
 */
public class MotionServiceUtility {

    private Context context;
    private PendingIntent pintent;
    private AlarmManager alarm;
    private Intent iService;

    public MotionServiceUtility(Context context) {
        super();
        this.context = context;
        iService = new Intent(context, MotionService.class);
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pintent = PendingIntent.getService(context, 0, iService, 0);
    }

    public void start() {
        startBackgroundScan();
    }

    public void stop() {
        stopBackgroundScan();
    }

    private void stopBackgroundScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            alarm.cancel(pintent);
            context.stopService(iService);
        }
    }

    private void startBackgroundScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
            context.startService(iService);
    }
}
