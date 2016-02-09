package com.gigigo.orchextra.di.modules.device;

import com.gigigo.ggglib.permissions.PermissionChecker;
import com.gigigo.orchextra.device.GoogleApiClientConnector;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.device.permissions.PermissionLocationImp;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface DeviceModuleProvider extends
    BluetoothModuleProvider,
    ActionsModuleProvider,
    NotificationsModuleProvider,
    GeolocationModuleProvider{

    AndroidApp provideAndroidApp();
    AndroidDevice provideAndroidDevice();
    GoogleApiClientConnector provideGoogleApiClientConnector();
    PermissionChecker providePermissionChecker();
    PermissionLocationImp providePermissionLocationImp();

}
