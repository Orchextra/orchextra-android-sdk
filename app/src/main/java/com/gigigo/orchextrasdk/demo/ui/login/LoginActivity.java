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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.geofence.OxGeofenceImp;
import com.gigigo.orchextra.indoorpositioning.OxIndoorPositioningImp;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.MainActivity;
import com.gigigo.orchextrasdk.demo.ui.settings.SettingsActivity;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

  public static final String API_KEY_KEY = "api_key_key";
  Orchextra orchextra;
  private String apiKey = "";
  private String apiSecret = "";
  List<ProjectData> projectDataList;
  EditText projectNameEditText;
  EditText apiKeyEditText;
  EditText apiSecretEditText;
  boolean doubleTap = false;
  int currentProject = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    projectDataList = ProjectData.getDefaultProjectDataList();
    initView();
    loadProjectData();
  }

  private void initView() {
    initToolbar();

    projectNameEditText = findViewById(R.id.projectName_editText);
    apiSecretEditText = findViewById(R.id.apiSecret_editText);
    apiKeyEditText = findViewById(R.id.apiKey_editText);
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

    Button startButton = findViewById(R.id.start_button);
    startButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        makeLogin();
      }
    });

    View logoIv = findViewById(R.id.logo_iv);
    logoIv.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (doubleTap) {

          if (currentProject >= projectDataList.size() - 1) {
            currentProject = 0;
          } else {
            currentProject++;
          }

          loadProjectData();
          return;
        }

        doubleTap = true;
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            doubleTap = false;
          }
        }, 500);
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

  void loadProjectData() {

    apiKeyEditText.setText(projectDataList.get(currentProject).getApiKey());
    apiSecretEditText.setText(projectDataList.get(currentProject).getApiSecret());
  }

  void makeLogin() {

    apiKey = apiKeyEditText.getText().toString();
    apiSecret = apiSecretEditText.getText().toString();

    if (apiKey.isEmpty() || apiSecret.isEmpty()) {
      Toast.makeText(this, "Invalid data", Toast.LENGTH_SHORT).show();
    } else {
      initOrchextra();
    }
  }

  private void initOrchextra() {
    saveApiKey(apiKey);
    orchextra = Orchextra.INSTANCE;
    orchextra.setStatusListener(orchextraStatusListener);
    orchextra.setErrorListener(orchextraErrorListener);
    orchextra.init(getApplication(), apiKey, apiSecret, true);
    orchextra.setScanTime(30);
  }

  private void saveApiKey(String apiKey) {
    SharedPreferences sharedPreferences =
        getSharedPreferences("orchextra_demo", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(API_KEY_KEY, apiKey);
    editor.apply();
  }

  private OrchextraStatusListener orchextraStatusListener = new OrchextraStatusListener() {
    @Override public void onStatusChange(boolean isReady) {
      if (isReady) {
        orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(getApplication()));
        orchextra.getTriggerManager()
            .setIndoorPositioning(OxIndoorPositioningImp.Factory.create(getApplication()));

        orchextra.setNotificationActivityClass(SettingsActivity.class);

        Toast.makeText(getBaseContext(), "SDK ready", Toast.LENGTH_SHORT).show();
        MainActivity.open(LoginActivity.this);
        finish();
      } else {
        Toast.makeText(getBaseContext(), "SDK finished", Toast.LENGTH_SHORT).show();
      }
    }
  };

  private OrchextraErrorListener orchextraErrorListener = new OrchextraErrorListener() {
    @Override public void onError(@NonNull Error error) {
      Toast.makeText(getBaseContext(), "Error: " + error.getCode() + " - " + error.getMessage(),
          Toast.LENGTH_SHORT).show();
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