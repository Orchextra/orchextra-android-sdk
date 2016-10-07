package com.gigigo.orchextra.android.proximity.geofencing;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceRegisterImpl;
import com.gigigo.orchextra.device.geolocation.geofencing.GeofenceDeviceRegister;
import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofenceUpdates;

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

    @Mock GeofenceController geofenceController;

    private AndroidGeofenceRegisterImpl androidGeofenceRegisterImpl;

    @Before
    public void setUp() throws Exception {
        androidGeofenceRegisterImpl = new AndroidGeofenceRegisterImpl(geofenceDeviceRegister, configObservable, geofenceController);
    }

    @Test
    public void shouldRegisterGeofencesWhenIsFillGeofenceList() throws Exception {
        doNothing().when(geofenceDeviceRegister).register(any(OrchextraGeofenceUpdates.class));

        androidGeofenceRegisterImpl.registerGeofences(any(OrchextraGeofenceUpdates.class));

        verify(geofenceDeviceRegister).register(any(OrchextraGeofenceUpdates.class));
    }

    @Test
    public void shouldStartGeofenceRegister() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));

        androidGeofenceRegisterImpl.startGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
    }

    @Test
    public void shouldStartGeofenceRegisterOnlyOnce() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));

        androidGeofenceRegisterImpl.startGeofenceRegister();

        androidGeofenceRegisterImpl.startGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
    }

    @Test
    public void shouldDoNothingWhenIsNotInitializated() throws Exception {
        doNothing().when(configObservable).removeObserver(any(Observer.class));

        androidGeofenceRegisterImpl.stopGeofenceRegister();

        verify(configObservable, never()).removeObserver(any(Observer.class));
    }

    @Test
    public void shouldStopServeceWhenIsInitializated() throws Exception {
        doNothing().when(configObservable).registerObserver(any(Observer.class));
        doNothing().when(configObservable).removeObserver(any(Observer.class));

        androidGeofenceRegisterImpl.startGeofenceRegister();
        androidGeofenceRegisterImpl.stopGeofenceRegister();

        verify(configObservable).registerObserver(any(Observer.class));
        verify(configObservable).removeObserver(any(Observer.class));
    }
}