package com.gigigo.orchextrasdk.demo.ui.settings.edit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class EditActivity extends AppCompatActivity {

  private static final String TAG = "EditActivity";
  private static final String TYPE_EXTRA = "type_extra";
  public static final String CRM = "crm";
  public static final String DEVICE = "device";
  private LinearLayout container;
  private CustomFieldView idCf;
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
    idCf = (CustomFieldView) findViewById(R.id.idCf);
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
      saveCRM();
    } else {
      saveDevice();
    }
  }

  private void saveCRM() {

    String id = idCf.getValue().isEmpty() ? null : idCf.getValue();
    String gender = genderCf.getValue().isEmpty() ? null : genderCf.getValue();
    String birthDate = birthDateCf.getValue().isEmpty() ? null : birthDateCf.getValue();

    if (id == null) {
      Toast.makeText(this, "Id can not be empty", Toast.LENGTH_SHORT).show();
      hideLoading();
      return;
    }

    String crmTagsString = tagsCf.getValue().replace(" ", "");
    List<String> crmTags = null;
    if (!crmTagsString.isEmpty()) {
      crmTags = new ArrayList<>(Arrays.asList(crmTagsString.split(",")));
    }

    String crmBusinessUnitsString = businessUnitsCf.getValue().replace(" ", "");
    List<String> crmBusinessUnits = null;
    if (!crmBusinessUnitsString.isEmpty()) {
      crmBusinessUnits = new ArrayList<>(Arrays.asList(crmBusinessUnitsString.split(",")));
    }

    OxCRM oxCRM = new OxCRM(id, gender, birthDate, crmTags, crmBusinessUnits, null);
    crmManager.bindUser(oxCRM, new Function1<OxCRM, Unit>() {
      @Override public Unit invoke(OxCRM oxCRM) {
        crmUpdated = true;
        tagsUpdated = true;
        businessUnitUpdated = true;
        finishOnUpdate();
        return null;
      }
    });

    finishOnUpdate();
  }

  private void saveDevice() {
    crmUpdated = true;

    String deviceTagsString = deviceTagsCf.getValue().replace(" ", "");
    List<String> deviceTags = new ArrayList<>();
    if (!deviceTagsString.isEmpty()) {
      deviceTags = new ArrayList<>(Arrays.asList(deviceTagsString.split(",")));
    }

    String deviceBusinessUnitsString = deviceBusinessUnitsCf.getValue().replace(" ", "");
    List<String> deviceBusinessUnits = new ArrayList<>();
    if (!deviceBusinessUnitsString.isEmpty()) {
      deviceBusinessUnits = new ArrayList<>(Arrays.asList(deviceBusinessUnitsString.split(",")));
    }

    if (!deviceTags.isEmpty()) {
      crmManager.setDeviceTags(deviceTags, new Function1<OxDevice, Unit>() {
        @Override public Unit invoke(OxDevice oxDevice) {
          tagsUpdated = true;
          finishOnUpdate();
          return null;
        }
      });
    } else {
      tagsUpdated = true;
    }

    if (!deviceBusinessUnits.isEmpty()) {
      crmManager.setDeviceBussinesUnits(deviceBusinessUnits, new Function1<OxDevice, Unit>() {
        @Override public Unit invoke(OxDevice oxDevice) {
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

  void bindData(@Nullable OxCRM user) {
    idCf.setCustomField(new CustomField("", "", getString(R.string.crm_id)));
    genderCf.setCustomField(new CustomField("", "", getString(R.string.gender)));
    birthDateCf.setCustomField(new CustomField("", "", getString(R.string.birth_date)));
    tagsCf.setCustomField(new CustomField("", "", getString(R.string.tags)));
    businessUnitsCf.setCustomField(new CustomField("", "", getString(R.string.business_units)));

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
    }

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