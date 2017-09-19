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

package com.gigigo.orchextrasdk.demo.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.gigigo.orchextra.core.CrmManager;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.CustomField;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.core.domain.entities.OxCRM;
import com.gigigo.orchextra.core.domain.entities.OxDevice;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.MainActivity;
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity;
import com.gigigo.orchextrasdk.demo.ui.login.ProjectData;
import com.gigigo.orchextrasdk.demo.ui.settings.edit.EditActivity;
import com.gigigo.orchextrasdk.demo.utils.widget.CustomFieldView;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingsActivity extends AppCompatActivity {

  private static final String TAG = "SettingsActivity";
  private EditText projectNameTextView;
  private EditText apiKeyTextView;
  private EditText apiSecretTextView;
  private Button finishButton;
  Orchextra orchextra;
  CrmManager crmManager;

  public static void open(Context context) {
    Intent intent = new Intent(context, SettingsActivity.class);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    projectNameTextView = (EditText) findViewById(R.id.project_name_editText);
    apiKeyTextView = (EditText) findViewById(R.id.apiKey_editText);
    apiSecretTextView = (EditText) findViewById(R.id.apiSecret_editText);
    finishButton = (Button) findViewById(R.id.finish_button);

    initOrchextra();
    initView();
  }

  @Override protected void onResume() {
    super.onResume();
    showData();
  }

  private void initOrchextra() {
    orchextra = Orchextra.INSTANCE;
    crmManager = orchextra.getCrmManager();
    orchextra.setStatusListener(new OrchextraStatusListener() {
      @Override public void onStatusChange(boolean isReady) {
        if (!isReady) {
          MainActivity.open(SettingsActivity.this);
          finish();
        }
      }
    });
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(SettingsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  private void initView() {
    initToolbar();
    showProject();
    initLogOutButton();
  }

  private void initLogOutButton() {
    if (orchextra.isReady()) {
      finishButton.setVisibility(View.VISIBLE);
      finishButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          orchextra.finish();
        }
      });
    } else {
      finishButton.setVisibility(View.GONE);
    }
  }

  private void showProject() {
    ProjectData projectData =
        ProjectData.getProjectDataByApiKey(ProjectData.getDefaultProjectDataList(), getApiKey());

    if (projectData != null) {
      projectNameTextView.setText(projectData.getName());
      apiKeyTextView.setText(projectData.getApiKey());
      apiSecretTextView.setText(projectData.getApiSecret());
    } else {
      projectNameTextView.setText("Custom");
      apiKeyTextView.setText(getApiKey());
      apiSecretTextView.setText("***");
    }
  }

  private void showData() {
    crmManager.getCurrentUser(new Function1<OxCRM, Unit>() {
      @Override public Unit invoke(OxCRM user) {
        bindData(user);

        crmManager.getDevice(new Function1<OxDevice, Unit>() {
          @Override public Unit invoke(OxDevice device) {
            bindData(device);
            return null;
          }
        });
        return null;
      }
    });
  }

  void bindData(@Nullable OxCRM user) {
    LinearLayout container = (LinearLayout) findViewById(R.id.container);
    CustomFieldView idCf = (CustomFieldView) findViewById(R.id.idCf);
    CustomFieldView genderCf = (CustomFieldView) findViewById(R.id.genderCf);
    CustomFieldView birthDateCf = (CustomFieldView) findViewById(R.id.birthDateCf);
    CustomFieldView tagsCf = (CustomFieldView) findViewById(R.id.tagsCf);
    CustomFieldView businessUnitsCf = (CustomFieldView) findViewById(R.id.businessUnitsCf);
    View editCrmBtn = findViewById(R.id.editCrmBtn);
    editCrmBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        EditActivity.open(SettingsActivity.this, EditActivity.CRM);
      }
    });

    idCf.setCustomField(new CustomField("", "", getString(R.string.crm_id)));
    idCf.setEnabled(false);
    genderCf.setCustomField(new CustomField("", "", getString(R.string.gender)));
    genderCf.setEnabled(false);
    birthDateCf.setCustomField(new CustomField("", "", getString(R.string.birth_date)));
    birthDateCf.setEnabled(false);
    tagsCf.setCustomField(new CustomField("", "", getString(R.string.tags)));
    tagsCf.setEnabled(false);
    businessUnitsCf.setCustomField(new CustomField("", "", getString(R.string.business_units)));
    businessUnitsCf.setEnabled(false);

    Map<String, String> customFields = null;

    if (user != null) {
      idCf.setValue(user.getCrmId());
      genderCf.setValue(user.getGender());
      birthDateCf.setValue(user.getBirthDate());

      if (user.getTags() != null) {
        tagsCf.setValue(TextUtils.join(", ", user.getTags()));
      }

      if (user.getBusinessUnits() != null) {
        businessUnitsCf.setValue(TextUtils.join(", ", user.getBusinessUnits()));
      }
      customFields = user.getCustomFields();
    } else {
      idCf.setValue("");
      genderCf.setValue("");
      birthDateCf.setValue("");
      tagsCf.setValue("");
      businessUnitsCf.setValue("");
    }

    container.removeAllViews();
    List<CustomField> availableCustomFields = crmManager.getAvailableCustomFields();
    CustomFieldView customFieldView;

    for (CustomField customField : availableCustomFields) {
      customFieldView = new CustomFieldView(this);
      customFieldView.setCustomField(customField);
      customFieldView.setEnabled(false);
      if (customFields != null) {
        customFieldView.setValue(customFields.get(customField.getKey()));
      }
      container.addView(customFieldView);
    }
  }

  void bindData(@NonNull OxDevice device) {
    CustomFieldView deviceTagsCf = (CustomFieldView) findViewById(R.id.deviceTagsCf);
    CustomFieldView deviceBusinessUnitsCf =
        (CustomFieldView) findViewById(R.id.deviceBusinessUnitsCf);
    View editDeviceBtn = findViewById(R.id.editDeviceBtn);
    editDeviceBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        EditActivity.open(SettingsActivity.this, EditActivity.DEVICE);
      }
    });

    deviceTagsCf.setCustomField(new CustomField("", "", getString(R.string.tags)));
    deviceTagsCf.setValue(TextUtils.join(", ", device.getTags()));
    deviceTagsCf.setEnabled(false);
    deviceBusinessUnitsCf.setCustomField(
        new CustomField("", "", getString(R.string.business_units)));
    deviceBusinessUnitsCf.setValue(TextUtils.join(", ", device.getBusinessUnits()));
    deviceBusinessUnitsCf.setEnabled(false);
  }

  private void initToolbar() {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  private String getApiKey() {
    SharedPreferences sharedPreferences =
        getSharedPreferences("orchextra_demo", Context.MODE_PRIVATE);
    return sharedPreferences.getString(LoginActivity.API_KEY_KEY, "");
  }

  @Override protected void onDestroy() {
    orchextra.removeStatusListener();
    orchextra.removeErrorListener();
    super.onDestroy();
  }
}
