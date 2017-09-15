package com.gigigo.orchextrasdk.demo.ui.settings.edit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.gigigo.orchextra.core.CrmManager;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.domain.entities.CustomField;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.core.domain.entities.OxCRM;
import com.gigigo.orchextra.core.domain.entities.OxDevice;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.utils.widget.CustomFieldView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class EditActivity extends AppCompatActivity {

  private static final String TAG = "EditActivity";
  private static final String TYPE_EXTRA = "type_extra";
  public static final String CRM = "crm";
  public static final String DEVICE = "device";
  private LinearLayout container;
  private CustomFieldView genderCf;
  private CustomFieldView birthDateCf;
  private CustomFieldView tagsCf;
  private CustomFieldView businessUnitsCf;
  private CustomFieldView deviceTagsCf;
  private CustomFieldView deviceBusinessUnitsCf;
  private ProgressDialog loadingDialog;
  Orchextra orchextra;
  CrmManager crmManager;
  Boolean crmUpdated = false, tagsUpdated = false, businessUnitUpdated = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);

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
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        hideLoading();
        Log.e(TAG, error.toString());
        Toast.makeText(EditActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  private void initView() {
    initToolbar();
    View userView = findViewById(R.id.userView);
    View deviceView = findViewById(R.id.deviceView);
    container = (LinearLayout) findViewById(R.id.container);
    genderCf = (CustomFieldView) findViewById(R.id.genderCf);
    birthDateCf = (CustomFieldView) findViewById(R.id.birthDateCf);
    tagsCf = (CustomFieldView) findViewById(R.id.tagsCf);
    businessUnitsCf = (CustomFieldView) findViewById(R.id.businessUnitsCf);
    deviceTagsCf = (CustomFieldView) findViewById(R.id.deviceTagsCf);
    deviceBusinessUnitsCf = (CustomFieldView) findViewById(R.id.deviceBusinessUnitsCf);

    if (getType().equals(CRM)) {
      userView.setVisibility(View.VISIBLE);
      deviceView.setVisibility(View.GONE);
      setTitle(getString(R.string.user));
    } else {
      userView.setVisibility(View.GONE);
      deviceView.setVisibility(View.VISIBLE);
      setTitle(getString(R.string.device));
    }
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
        crmUpdated = false;
        tagsUpdated = false;
        businessUnitUpdated = false;

        saveData();
      }
    });
  }

  private void showData() {
    crmManager.getCurrentUser(new Function1<OxCRM, Unit>() {
      @Override public Unit invoke(OxCRM user) {
        bindData(user);
        return null;
      }
    });

    crmManager.getDevice(new Function1<OxDevice, Unit>() {
      @Override public Unit invoke(OxDevice device) {
        bindData(device);
        return null;
      }
    });
  }

  void saveData() {
    showLoading();
    if (getType().equals(CRM)) {

      String gender = genderCf.getValue();
      String birthDate = birthDateCf.getValue();
      String tags = tagsCf.getValue();
      String businessUnits = businessUnitsCf.getValue();

      OxCRM user = new OxCRM("", gender, birthDate, Collections.<String>emptyList(),
          Collections.<String>emptyList(), Collections.<String, String>emptyMap());

      crmManager.bindUser(user);

      hideLoading();
    } else {
      saveDevice();
    }
  }

  private void saveDevice() {
    crmUpdated = true;

    String deviceTagsString = deviceTagsCf.getValue();
    List<String> deviceTags = new ArrayList<>();
    if (!deviceTagsString.isEmpty()) {
      deviceTags = new ArrayList<>(Arrays.asList(deviceTagsString.split(",")));
    }

    String deviceBusinessUnitsString = deviceBusinessUnitsCf.getValue();
    List<String> deviceBusinessUnits = new ArrayList<>();
    if (!deviceBusinessUnitsString.isEmpty()) {
      deviceBusinessUnits = new ArrayList<>(Arrays.asList(deviceBusinessUnitsString.split(",")));
    }

    if (!deviceTags.isEmpty()) {
      crmManager.setDeviceTags(deviceTags, new Function0<Unit>() {
        @Override public Unit invoke() {
          tagsUpdated = true;
          finishOnUpdate();
          return null;
        }
      });
    } else {
      tagsUpdated = true;
    }

    if (!deviceBusinessUnits.isEmpty()) {
      crmManager.setDeviceBussinesUnits(deviceBusinessUnits, new Function0<Unit>() {
        @Override public Unit invoke() {
          businessUnitUpdated = true;
          finishOnUpdate();
          return null;
        }
      });
    } else {
      businessUnitUpdated = true;
    }

    finishOnUpdate();
  }

  void finishOnUpdate() {
    if (crmUpdated && tagsUpdated && businessUnitUpdated) {
      hideLoading();
      onBackPressed();
    }
  }

  void bindData(@NonNull OxCRM user) {
    genderCf.setCustomField(new CustomField("", "", getString(R.string.gender)));
    genderCf.setValue(user.getGender());

    birthDateCf.setCustomField(new CustomField("", "", getString(R.string.birth_date)));
    birthDateCf.setValue(user.getBirthDate());

    tagsCf.setCustomField(new CustomField("", "", getString(R.string.tags)));
    tagsCf.setValue(TextUtils.join(", ", user.getTags()));

    businessUnitsCf.setCustomField(new CustomField("", "", getString(R.string.business_units)));
    businessUnitsCf.setValue(TextUtils.join(", ", user.getTags()));

    container.removeAllViews();
    List<CustomField> customFields = crmManager.getAvailableCustomFields();
    CustomFieldView customFieldView;

    for (CustomField customField : customFields) {
      customFieldView = new CustomFieldView(this);
      customFieldView.setCustomField(customField);
      container.addView(customFieldView);
    }
  }

  void bindData(@NonNull OxDevice device) {
    deviceTagsCf.setCustomField(new CustomField("", "", "Tags"));
    deviceTagsCf.setValue(TextUtils.join(", ", device.getTags()));
    deviceBusinessUnitsCf.setCustomField(new CustomField("", "", "Business units"));
    deviceBusinessUnitsCf.setValue(TextUtils.join(", ", device.getBusinessUnits()));
  }

  private String getType() {
    return getIntent().getStringExtra(TYPE_EXTRA);
  }

  void showLoading() {
    loadingDialog = ProgressDialog.show(EditActivity.this, "", "Loading. Please wait...", true);
  }

  void hideLoading() {
    if (loadingDialog != null) {
      loadingDialog.dismiss();
    }
  }

  @Override protected void onDestroy() {
    orchextra.removeErrorListener();
    super.onDestroy();
  }

  public static void open(Context context, String type) {
    Intent intent = new Intent(context, EditActivity.class);
    intent.putExtra(TYPE_EXTRA, type);
    context.startActivity(intent);
  }
}
