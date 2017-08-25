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

package gigigo.com.orchextrasdk.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.gigigo.orchextra.core.Orchextra;
import gigigo.com.orchextrasdk.R;
import gigigo.com.orchextrasdk.demo.geofences.GeofencesFragment;
import gigigo.com.orchextrasdk.demo.scanner.ScannerFragment;
import gigigo.com.orchextrasdk.demo.triggerlog.TriggerLogFragment;
import gigigo.com.orchextrasdk.settings.SettingsActivity;

public class OrchextraDemoActivity extends AppCompatActivity {

  private ScannerFragment scannerFragment;
  private TriggerLogFragment triggerLogFragment;
  private GeofencesFragment geofencesFragment;
  private BottomNavigationView navigation;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_orchextra_demo);

    initView();

    scannerFragment = ScannerFragment.newInstance();
    geofencesFragment = GeofencesFragment.newInstance();
    triggerLogFragment = TriggerLogFragment.newInstance();

    if (savedInstanceState == null) {
      navigation.setSelectedItemId(R.id.navigation_scanner);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_settings, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {

    if (item.getItemId() == R.id.action_settings) {
      SettingsActivity.open(this);
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    Orchextra orchextra = Orchextra.INSTANCE;
    if (!orchextra.isReady()) {
      finish();
    }
  }

  private void initView() {

    initToolbar();

    navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {

          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.navigation_scanner:
                showView(scannerFragment);
                return true;
              case R.id.navigation_geofences:
                showView(geofencesFragment);
                return true;
              case R.id.navigation_triggers_log:
                showView(triggerLogFragment);
                return true;
            }
            return false;
          }
        });
  }

  private void initToolbar() {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
  }

  private void showView(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit();
  }

  public static void open(Context context) {
    Intent intent = new Intent(context, OrchextraDemoActivity.class);
    context.startActivity(intent);
  }
}
