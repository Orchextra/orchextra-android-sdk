package gigigo.com.orchextrasdk.adonservices;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by nubor on 16/11/2016.
 */
//fixme will be  a great idea , keep the time of repeating in preferences, realm or whatveru want,
//beacose the receiver when reeboot re-programe the same time for alarmmanager
public class UpdateConfigUtility {
    //todo this context must to be Appcompat, because is necesary for askiing about permissions
    Context mContext;
    private AlarmManager alarm;
    public final static String ACTION_REFRESH_CONFIG = "android.intent.action.REFRESH_CONFIGURATION";

    public UpdateConfigUtility(Context mContext) {
        this.mContext = mContext;
        alarm = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    /***
     * @param eachMiliseconds long: minimum time interval between location updates, in milliseconds, recomendede >60000ms
     * @param mindistance     float: minimum distance between location updates, in meters
     */
    //drain battery if use LocationManager
    @Deprecated
    public void createUpdateConfigurationByTime(long eachMiliseconds, float mindistance) {
        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            Intent intent = new Intent(mContext, UpdateConfigReceiver.class);
            intent.setAction(ACTION_REFRESH_CONFIG);
            PendingIntent BroadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("Permissions Problem, maybe never do execute and grant permissions");
                return;
            }
            System.out.println("BROADCAST UpdateConfigurationByTime Suscribe!!");
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, eachMiliseconds, mindistance, BroadCastPending);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createUpdateConfigurationByTime(long time) {
        Intent intent = new Intent(mContext, UpdateConfigReceiver.class);
        intent.setAction(ACTION_REFRESH_CONFIG);
        PendingIntent broadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, broadCastPending); // each time
        mContext.startService(intent);

    }
}
