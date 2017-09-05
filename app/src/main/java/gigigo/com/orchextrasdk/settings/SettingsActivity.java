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

package gigigo.com.orchextrasdk.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import gigigo.com.orchextrasdk.R;
import gigigo.com.orchextrasdk.demo.MainActivity;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

  private static final String TAG = "SettingsActivity";
  private Orchextra orchextra;
  private EditText projectNameTextView;
  private EditText apiKeyTextView;
  private EditText apiSecretTextView;
  private Button finishButton;

  private SettingsPresenter settingsPresenter;

  public static void open(Context context) {
    Intent intent = new Intent(context, SettingsActivity.class);
    context.startActivity(intent);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    settingsPresenter = new SettingsPresenter(this, new CredentialsPreferenceManager(this));

    projectNameTextView = (EditText) findViewById(R.id.project_name_editText);
    apiKeyTextView = (EditText) findViewById(R.id.apiKey_editText);
    apiSecretTextView = (EditText) findViewById(R.id.apiSecret_editText);
    finishButton = (Button) findViewById(R.id.finish_button);

    initView();
  }

  @Override public void setupOrchextra() {
    orchextra = Orchextra.INSTANCE;
    orchextra.setOrchextraStatusListener(new OrchextraStatusListener() {
      @Override public void onStatusChange(boolean isReady) {
        if (isReady) {
          enableLogout();
          apiKeyTextView.setEnabled(!isReady);
          apiSecretTextView.setEnabled(!isReady);
        } else {
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

  @Override public void finishOrchextra() {
    orchextra.finish();
  }

  private void initView() {
    initToolbar();

    settingsPresenter.uiReady();
  }

  @Override public void enableLogout() {
    if (orchextra.isReady()) {
      finishButton.setVisibility(View.VISIBLE);
      finishButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          settingsPresenter.doLogout();
        }
      });
    } else {
      finishButton.setVisibility(View.GONE);
    }
  }

  @Override public void showProjectCredentials(String apiKey, String apiSecret) {
    apiKeyTextView.setText(apiKey);
    apiSecretTextView.setText(apiSecret);

    apiKeyTextView.setEnabled(!orchextra.isReady());
    apiSecretTextView.setEnabled(!orchextra.isReady());
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
}
