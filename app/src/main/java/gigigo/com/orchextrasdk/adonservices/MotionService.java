package gigigo.com.orchextrasdk.adonservices;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.sdk.background.OrchextraBackgroundService;
import com.gigigo.orchextra.sdk.background.OrchextraBootBroadcastReceiver;

import java.util.GregorianCalendar;

import gigigo.com.orchextrasdk.R;

/**
 * Created by Alberto on 15/03/2016.
 */

//the call toconfiguration by the other adonservices wakeup the services of geofencing and beacon again
//with the current beacons implementation, when we are in back the restart can not call initRanging

public class MotionService extends Service implements SensorEventListener {
    SensorManager senSensorManager;
    Sensor senAccelerometer;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private static final int MOVE_THRESHOLD = 140;
    private static final int NO_MOVE_COUNTER_TO_STOP = 250; //250 loops 30sg with out no move
    private static int noMoveCounter = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        //debug Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        senSensorManager.unregisterListener(this, senAccelerometer);
        //debug Toast.makeText(getApplicationContext(), "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL, 400000);
            //values greates than 400000, not detct moves correclty
        } else {
            senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }


        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                // sendLocalNotification(speed > MOVE_THRESHOLD, noMoveCounter > NO_MOVE_COUNTER_TO_STOP);

                if (speed > MOVE_THRESHOLD) {
                    System.out.println("``\n\n\n MOVING" + speed + "POWER " + senAccelerometer.getPower());
                    noMoveCounter = 0;//reset

                    if (readIsStopped()) {
                        startServices();
                        System.out.println("********************STARTING SERVICES");
                    }
                } else {
                    System.out.println("++NO MOVE" + speed + "No Moving times->" + noMoveCounter + "POWER " + senAccelerometer.getPower());
                    noMoveCounter = noMoveCounter + 1;
                    if (noMoveCounter > NO_MOVE_COUNTER_TO_STOP) {
                        if (!readIsStopped()) {
                            stopServices();
                            System.out.println("STOPPING SERVICES*************************************");
                        } else
                            System.out.println("*The SERVICES already stopped");
                    }
                }

                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void stopServices() {
        saveIsStopped(true);
        Orchextra.pause(this.getApplicationContext());
    }

    private void startServices() {
        saveIsStopped(false);
        Orchextra.reStart(this.getApplicationContext());
    }

    private void saveIsStopped(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("MoveStopped", value);
        editor.apply();
    }

    private boolean readIsStopped() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("MoveStopped", false);
    }

    //region Generación de Notificación
    private void sendLocalNotification(boolean isMove, boolean needStopService) {

        final String MOVE_TITLE = "MOVE";
        final String NO_MOVE_TITLE = "NO_MOVE";
        final String NO_MOVE_STOP_TITLE = "NO_MOVE_STOP_SERVICES";

        final String MOVE_BODY = "MENEA EL BULLARETE";
        final String NO_MOVE_BODY = "Te Mueves - q los 0j0s de Espinete";
        final String NO_MOVE_STOP_BODY = "NO TE HAS MOVIDO POR 250sg,stoping services";

        final String MOVE_ACTION = "MENEA_EL_BULLARETE_action";
        final String NO_MOVE_ACTION = "Te_Mueves_-_q_los_0j0s_de_Espinete_action";
        final String NO_MOVE_STOP_ACTION = "NO_MOVE_STOP_action";

        final int MOVE_NOT_ID = 100;
        final int NO_MOVE_NOT_ID = 101;
        final int NO_MOVE_STOP_ID = 501;

        String Action, Title, Body = "";
        int id_notification = 0;
        int smallIcon = 0;

        if (!needStopService) {
            Action = isMove ? MOVE_ACTION : NO_MOVE_ACTION;
            Title = isMove ? MOVE_TITLE : NO_MOVE_TITLE;
            Body = isMove ? MOVE_BODY : NO_MOVE_BODY;
            id_notification = isMove ? MOVE_NOT_ID : NO_MOVE_NOT_ID;
            smallIcon = isMove ? R.drawable.ic_heart_grey600_24dp : R.drawable.ic_heart_outline_grey600_24dp;
        } else {
            Action = NO_MOVE_STOP_ACTION;
            Title = NO_MOVE_STOP_TITLE;
            Body = NO_MOVE_STOP_BODY;
            id_notification = NO_MOVE_STOP_ID;
            smallIcon = R.drawable.ic_heart_black_24dp;
        }
        Intent intent = new Intent(this, MotionService.class);
        intent.setAction(Action + new GregorianCalendar().getTimeInMillis());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(smallIcon)
                .setContentTitle(Title)
                .setContentText(Body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager systemService = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (systemService != null)
            systemService.notify(id_notification, builder.build());
    }
    //endregion
}
