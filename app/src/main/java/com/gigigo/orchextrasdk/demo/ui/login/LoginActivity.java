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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.MainActivity;
import com.gigigo.orchextrasdk.demo.utils.integration.Ox3ManagerImp;
import com.gigigo.orchextrasdk.demo.utils.integration.OxManager;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

  public static final String API_KEY_KEY = "api_key_key";
  private OxManager oxManager;
  private String apiKey = "";
  private String apiSecret = "";
  List<ProjectData> projectDataList;
  EditText projectNameEditText;
  EditText apiKeyEditText;
  EditText apiSecretEditText;
  TextView errorTextView;
  boolean doubleTap = false;
  int currentProject = 0;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    oxManager = new Ox3ManagerImp();
    projectDataList = ProjectData.getDefaultProjectDataList();
    initView();
    loadProjectData();
  }

  private void initView() {
    initToolbar();

    projectNameEditText = findViewById(R.id.projectName_editText);
    errorTextView = findViewById(R.id.errorTextView);
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
        hideError();
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
      showError("Invalid data");
    } else {
      initOrchextra();
    }
  }

  private void initOrchextra() {
    saveApiKey(apiKey);

    OxManager.Config config = new OxManager.Config.Builder().apiKey(apiKey)
        .apiSecret(apiSecret)
        .firebaseApiKey("AIzaSyDlMIjwx2r0oc0W7O4WPb7CvRhjCVHOZBk")
        .firebaseApplicationId("1:327008883283:android:5a0b51c3ef8892e0")
        .build();

    oxManager.init(getApplication(), config, new OxManager.StatusListener() {
      @Override public void isReady() {
        MainActivity.open(LoginActivity.this);
        finish();
      }

      @Override public void onError(@NonNull String error) {
        showError("Error - " + error);
      }
    });
  }

  private void saveApiKey(String apiKey) {
    SharedPreferences sharedPreferences =
        getSharedPreferences("orchextra_demo", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(API_KEY_KEY, apiKey);
    editor.apply();
  }

  void showError(String error) {
    errorTextView.setVisibility(View.VISIBLE);
    errorTextView.setText("Error: " + error);
  }

  private void hideError() {
    errorTextView.setVisibility(View.GONE);
  }

  @Override protected void onDestroy() {
    oxManager.removeListeners();
    super.onDestroy();
  }

  public static void open(Context context) {
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }
}