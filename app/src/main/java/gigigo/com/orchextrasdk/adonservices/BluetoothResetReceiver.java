package gigigo.com.orchextrasdk.adonservices;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

/**
 * Created by nubor on 15/12/2016.
 */
public class BluetoothResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive", "This is non a error onReceive-BluetoothResetReceiver");
        if (BluetoothResetUtility.ACTION_RESET_BT.equals(intent.getAction())) {
            System.out.println("ACTION" + intent.getAction());
            reEnabledBluetooth(context);
        }
    }
    private void reEnabledBluetooth(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

            if (((BluetoothManager) context.getApplicationContext()
                    .getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter().isEnabled()) {
                System.out.println("==========================================================Disable BT");
                //turn off
                BluetoothAdapter.getDefaultAdapter().disable();
                //wait 5sg and turn on again
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BluetoothAdapter.getDefaultAdapter().enable();
                        System.out.println("try re-Enable BT===============================================");
                    }
                }, 5000);
            }
        }

    }

}
