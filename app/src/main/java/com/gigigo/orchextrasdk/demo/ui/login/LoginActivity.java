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

package com.gigigo.orchextrasdk.demo.ui.login;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.geofence.OxGeofenceImp;
import com.gigigo.orchextra.indoorpositioning.OxIndoorPositioningImp;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.MainActivity;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends AppCompatActivity {

  private static final int PERMISSIONS_REQUEST_LOCATION = 1;
  Orchextra orchextra;
  private String apiKey = "";
  private String apiSecret = "";
  List<ProjectData> projectDataList;
  EditText projectNameEditText;
  EditText apiKeyEditText;
  EditText apiSecretEditText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    projectDataList = ProjectData.getDefaultProjectDataList();
    initView();
    loadInitialData();
  }

  private void initView() {
    initToolbar();

    projectNameEditText = (EditText) findViewById(R.id.projectName_editText);
    apiSecretEditText = (EditText) findViewById(R.id.apiSecret_editText);
    apiKeyEditText = (EditText) findViewById(R.id.apiKey_editText);
    apiKeyEditText.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable s) {

        ProjectData projectData = ProjectData.getProjectDataByApiKey(projectDataList, s.toString());
        if (projectData != null) {
          projectNameEditText.setText(projectData.getName());
        } else {
          projectNameEditText.setText("Custom");
        }
      }
    });

    Button startButton = (Button) findViewById(R.id.start_button);
    startButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        makeLogin();
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

  private void loadInitialData() {

    apiKeyEditText.setText(projectDataList.get(0).getApiKey());
    apiSecretEditText.setText(projectDataList.get(0).getApiSecret());
  }

  void makeLogin() {

    apiKey = apiKeyEditText.getText().toString();
    apiSecret = apiSecretEditText.getText().toString();

    if (apiKey.isEmpty() || apiSecret.isEmpty()) {
      Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
    } else {

      if (ContextCompat.checkSelfPermission(LoginActivity.this, ACCESS_FINE_LOCATION)
          == PackageManager.PERMISSION_GRANTED) {
        initOrchextra();
      } else {
        requestPermission();
      }
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
          initOrchextra();
        } else {
          Toast.makeText(LoginActivity.this, "Lo necesitamos!!!", Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  private void initOrchextra() {
    orchextra = Orchextra.INSTANCE;
    orchextra.setStatusListener(orchextraStatusListener);
    orchextra.init(getApplication(), apiKey, apiSecret, true);
  }

  private OrchextraStatusListener orchextraStatusListener = new OrchextraStatusListener() {
    @Override public void onStatusChange(boolean isReady) {
      if (isReady) {
        orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(getApplication()));
        orchextra.getTriggerManager()
            .setIndoorPositioning(OxIndoorPositioningImp.Factory.create(getApplication()));

        Toast.makeText(getBaseContext(), "SDK ready", Toast.LENGTH_SHORT).show();
        MainActivity.open(LoginActivity.this);
        finish();
      } else {
        Toast.makeText(getBaseContext(), "SDK finished", Toast.LENGTH_SHORT).show();
      }
    }
  };

  @Override protected void onDestroy() {
    orchextra.removeStatusListener();
    orchextra.removeErrorListener();
    super.onDestroy();
  }

  public static void open(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }
}