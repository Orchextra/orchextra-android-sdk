package gigigo.com.orchextrasdk;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by nubor on 16/11/2016.
 */
//lo creo aqki para q el copy y paste sea aun mas sencillo, ya q si lo meto como metodo al app q es dnd acabará
//el tema de imports y todo eso queda menos claro.Simplemente x ello, no more ;)

public class UpdateConfigWrapper {
    //todo este contexto se debe referir a una Appcompat ya que lo utiliza para preguntar por los permisos
    Context mContext;

    public UpdateConfigWrapper(Context mContext) {
        this.mContext = mContext;
    }

    /***
     *
     * @param eachMiliseconds long: minimum time interval between location updates, in milliseconds
     * @param mindistance float: minimum distance between location updates, in meters
     */
    public void createUpdateConfigurationByTime(long eachMiliseconds, float mindistance) {
        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            Intent intent = new Intent(mContext, UpdateConfigReceiver.class);
            PendingIntent BroadCastPending = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "PROBLEMA CON LOS PERMISOS!!, NO ESTÄ HABILITADO", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mContext, "BROADCAST SUSCRITO!! fuck yeah", Toast.LENGTH_SHORT).show();
            //todo podemos agregar otros provider mas, xo ello tb implica la peition de permisos de momento recae en la app, asi
            //no more por el momento tiramos   con--> GPS_PROVIDER
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, eachMiliseconds, mindistance, BroadCastPending);

        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(mContext, "OUCH! algo ha ido malamente", Toast.LENGTH_SHORT).show();
        }
    }
}
