package gigigo.com.orchextrasdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.gigigo.orchextra.Orchextra;

/**
 * Created by nubor on 16/11/2016.
 */
public class UpdateConfigReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Toast.makeText(context, "hola post reinicio registro el LocationUpdater, para q sea sensible a eio", Toast.LENGTH_LONG).show();
            UpdateConfigWrapper updater = new UpdateConfigWrapper(context);
            updater.createUpdateConfigurationByTime(1000, 10000); //todo estos
        } else {
            Toast.makeText(context, "hola holita, agendada llamada al configuration", Toast.LENGTH_LONG).show();
            //llamada al configuration, de la forma más retrocompatible(no todas las versiones de ox tienen a dia de hoy el commitConfiguration o el refreshconfiguration)
            Orchextra.start();
        }
    }
}
