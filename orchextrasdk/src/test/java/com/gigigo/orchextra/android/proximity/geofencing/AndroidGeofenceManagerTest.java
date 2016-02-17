package com.gigigo.orchextra.android.proximity.geofencing;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceRegisterImp;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofenceUpdates;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AndroidGeofenceManagerTest {

    @Mock GeofenceDeviceRegister geofenceDeviceRegister;

    @Mock
    ConfigObservable configObservable;

    @Mock
    OrchextraGeofenceUpdates orchextraGeofenceUpdates;

    private AndroidGeofenceRegisterImp androidGeofenceRegisterImp;

    @Before
    public void setUp() throws Exception {
        androidGeofenceRegisterImp = new AndroidGeofenceRegisterImp(geofenceDeviceRegister, configObservable);
    }

    @Test
    public void shouldRegisterGeofencesWhenIsFillGeofenceList() throws Exception {
        doNothing().when(geofenceDeviceRegister).register(any(OrchextraGeofenceUpdates.class));

        androidGeofenceRegisterImp.registerGeofences(any(OrchextraGeofenceUpdates.class));

        verify(geofenceDeviceRegister).register(any(OrchextraGeofenceUpdates.class));
    }

    @Test
    public void shouldStartGeofenceRegister() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));

        androidGeofenceRegisterImp.startGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
    }

    @Test
    public void shouldStartGeofenceRegisterOnlyOnce() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));

        androidGeofenceRegisterImp.startGeofenceRegister();

        androidGeofenceRegisterImp.startGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
    }

    @Test
    public void shouldDoNothingWhenIsNotInitializated() throws Exception {
        doNothing().when(configObservable).removeObserver(any(Observer.class));

        androidGeofenceRegisterImp.stopGeofenceRegister();

        verify(configObservable, never()).removeObserver(any(Observer.class));
    }

    @Test
    public void shouldStopServeceWhenIsInitializated() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));
        doNothing().when(configObservable).removeObserver(any(Observer.class));

        androidGeofenceRegisterImp.startGeofenceRegister();
        androidGeofenceRegisterImp.stopGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
        verify(configObservable).removeObserver(any(Observer.class));
    }
}