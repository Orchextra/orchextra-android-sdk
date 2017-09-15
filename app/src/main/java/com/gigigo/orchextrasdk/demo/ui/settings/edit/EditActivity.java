package com.gigigo.orchextrasdk.demo.ui.settings.edit;

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
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.CustomField;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.core.domain.entities.OxCRM;
import com.gigigo.orchextra.core.domain.entities.OxDevice;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.MainActivity;
import com.gigigo.orchextrasdk.demo.utils.widget.CustomFieldView;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class EditActivity extends AppCompatActivity {

  private static final String TAG = "EditActivity";
  private static final String TYPE_EXTRA = "type_extra";
  public static final String CRM = "crm";
  public static final String DEVICE = "device";
  private View userView;
  private View deviceView;
  Orchextra orchextra;
  CrmManager crmManager;

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
    orchextra.setStatusListener(new OrchextraStatusListener() {
      @Override public void onStatusChange(boolean isReady) {
        if (!isReady) {
          MainActivity.open(EditActivity.this);
          finish();
        }
      }
    });
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(EditActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  private void initView() {
    initToolbar();
    userView = findViewById(R.id.userView);
    deviceView = findViewById(R.id.deviceView);

    if (getType().equals(CRM)) {
      userView.setVisibility(View.VISIBLE);
      deviceView.setVisibility(View.GONE);
      setTitle("User");
    } else {
      userView.setVisibility(View.GONE);
      deviceView.setVisibility(View.VISIBLE);
      setTitle("Device");
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
        onBackPressed();
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

  void bindData(@NonNull OxCRM user) {
    LinearLayout container = (LinearLayout) findViewById(R.id.container);
    CustomFieldView genderCf = (CustomFieldView) findViewById(R.id.genderCf);
    CustomFieldView birthDateCf = (CustomFieldView) findViewById(R.id.birthDateCf);
    CustomFieldView tagsCf = (CustomFieldView) findViewById(R.id.tagsCf);
    CustomFieldView businessUnitsCf = (CustomFieldView) findViewById(R.id.businessUnitsCf);

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
    CustomFieldView deviceTagsCf = (CustomFieldView) findViewById(R.id.deviceTagsCf);
    CustomFieldView deviceBusinessUnitsCf =
        (CustomFieldView) findViewById(R.id.deviceBusinessUnitsCf);

    deviceTagsCf.setCustomField(new CustomField("", "", "Tags"));
    deviceTagsCf.setValue(TextUtils.join(", ", device.getTags()));
    deviceBusinessUnitsCf.setCustomField(new CustomField("", "", "Business units"));
    deviceBusinessUnitsCf.setValue(TextUtils.join(", ", device.getBusinessUnits()));
  }

  private String getType() {
    return getIntent().getStringExtra(TYPE_EXTRA);
  }

  public static void open(Context context, String type) {
    Intent intent = new Intent(context, EditActivity.class);
    intent.putExtra(TYPE_EXTRA, type);
    context.startActivity(intent);
  }
}
