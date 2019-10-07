/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextrasdk.demo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.gigigo.orchextra.core.domain.entities.OxPoint;
import com.gigigo.orchextra.core.domain.location.OxLocationListener;
import com.gigigo.orchextra.core.domain.location.OxLocationUpdates;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.geofences.GeofencesFragment;
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity;
import com.gigigo.orchextrasdk.demo.ui.scanner.ScannerFragment;
import com.gigigo.orchextrasdk.demo.ui.settings.SettingsActivity;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.TriggerLogFragment;
import com.gigigo.orchextrasdk.demo.utils.integration.Ox3ManagerImp;
import com.gigigo.orchextrasdk.demo.utils.integration.OxManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    ScannerFragment scannerFragment;
    TriggerLogFragment triggerLogFragment;
    GeofencesFragment geofencesFragment;
    private BottomNavigationView navigation;
    private OxManager oxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateServiceLocation();
        setContentView(R.layout.activity_main);

        initView();
        oxManager = new Ox3ManagerImp();

        scannerFragment = ScannerFragment.newInstance();
        geofencesFragment = GeofencesFragment.newInstance();
        triggerLogFragment = TriggerLogFragment.newInstance();

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_scanner);
        }

        oxManager.setCustomSchemeReceiver(new OxManager.CustomActionListener() {
            @Override
            public void onCustomSchema(@NonNull String customSchema) {
                if ("custom://calculator".equals(customSchema)) {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.es/search?q=2%2B2&oq=2%2B2"));
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "CustomSchema: " + customSchema,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        oxManager.getToken(new OxManager.TokenReceiver() {
            @Override
            public void onGetToken(@NonNull String token) {
                Log.d(TAG, "Token: " + token);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            SettingsActivity.open(this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLocationServiceResume();
        if (!oxManager.isReady()) {
            LoginActivity.open(this);
            finish();
        }
    }

    private void initView() {

        initToolbar();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_scanner:
                                showView(scannerFragment);
                                setTitle(R.string.title_scanner);
                                return true;
                            case R.id.navigation_geofences:
                                showView(geofencesFragment);
                                setTitle(R.string.title_geofences);
                                return true;
                            case R.id.navigation_triggers_log:
                                showView(triggerLogFragment);
                                setTitle(R.string.title_triggers_log);
                                return true;
                        }
                        return false;
                    }
                });
    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    void showView(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // Location Service

    OxLocationUpdates locationManager = new OxLocationUpdates();

    private void onCreateServiceLocation() {
        locationManager.onCreate();
        locationManager.setListener(new OxLocationListener() {
            @Override
            public void onLocationUpdated(@NotNull OxPoint location) {
                Log.d(TAG, "OXPoint received");
            }

            @Override
            public void onLocationConnected() {
                locationManager.startLocationUpdates();
            }

            @Override
            public void onLocationDisconnected() {
                locationManager.stopLocationUpdates();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationManager.onStart(this);
    }

    private void onLocationServiceResume() {
        locationManager.onResume(this);
    }

    @Override
    protected void onPause() {
        locationManager.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        locationManager.onStop(this);
        super.onStop();
    }
}
