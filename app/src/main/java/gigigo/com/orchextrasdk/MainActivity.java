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

package gigigo.com.orchextrasdk;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.geofence.OxGeofenceImp;
import com.gigigo.orchextra.scanner.OxScannerImp;
import gigigo.com.orchextrasdk.demo.OrchextraDemoActivity;
import gigigo.com.orchextrasdk.settings.OrchextraPreferenceManager;
import gigigo.com.orchextrasdk.settings.SettingsActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private OrchextraPreferenceManager orchextraPreferenceManager;
  private static final int PERMISSIONS_REQUEST_LOCATION = 1;
  private Orchextra orchextra;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    orchextraPreferenceManager = new OrchextraPreferenceManager(this);
    orchextra = Orchextra.INSTANCE;
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });

    initView();
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

  private void initView() {

    initToolbar();

    Button startButton = (Button) findViewById(R.id.start_button);
    startButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
          orchextra.init(getApplication(), orchextraPreferenceManager.getApiKey(),
              orchextraPreferenceManager.getApiSecret(), orchextraStatusListener);
        } else {
          requestPermission();
        }
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

  private void requestPermission() {
    if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
        Toast.makeText(this, "Expanation!!!", Toast.LENGTH_SHORT).show();
      } else {
        ActivityCompat.requestPermissions(this, new String[] { ACCESS_FINE_LOCATION },
            PERMISSIONS_REQUEST_LOCATION);
      }
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
      @NonNull int[] grantResults) {

    switch (requestCode) {
      case PERMISSIONS_REQUEST_LOCATION: {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          orchextra.init(getApplication(), orchextraPreferenceManager.getApiKey(),
              orchextraPreferenceManager.getApiSecret(), orchextraStatusListener);
        } else {
          Toast.makeText(MainActivity.this, "Lo necesitamos!!!", Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  private OrchextraStatusListener orchextraStatusListener = new OrchextraStatusListener() {
    @Override public void onStatusChange(boolean isReady) {
      if (isReady) {
        orchextra.getTriggerManager().setScanner(OxScannerImp.Factory.create(MainActivity.this));
        orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(MainActivity.this));
        //orchextra.getTriggerManager()
        //    .setIndoorPositioning(OxIndoorPositioningImp.Factory.create(MainActivity.this));

        Toast.makeText(MainActivity.this, "SDK ready", Toast.LENGTH_SHORT).show();
        OrchextraDemoActivity.open(MainActivity.this);
        finish();
      } else {
        Toast.makeText(MainActivity.this, "SDK finished", Toast.LENGTH_SHORT).show();
      }
    }
  };

  public static void open(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    context.startActivity(intent);
  }
}
