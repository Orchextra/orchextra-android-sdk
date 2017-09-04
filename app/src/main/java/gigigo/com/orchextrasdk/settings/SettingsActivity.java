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

public class SettingsActivity extends AppCompatActivity {

  private static final String TAG = "SettingsActivity";
  private CredentialsPreferenceManager credentialsPreferenceManager;
  private Orchextra orchextra;
  private EditText apiKeyTv;
  private EditText apiSecretTv;
  private Button finishButton;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    apiKeyTv = (EditText) findViewById(R.id.api_key_tv);
    apiSecretTv = (EditText) findViewById(R.id.api_secret_tv);
    finishButton = (Button) findViewById(R.id.finish_button);
    credentialsPreferenceManager = new CredentialsPreferenceManager(this);

    orchextra = Orchextra.INSTANCE;
    orchextra.setOrchextraStatusListener(new OrchextraStatusListener() {
      @Override public void onStatusChange(boolean isReady) {
        initFinishButton();
        apiKeyTv.setEnabled(!isReady);
        apiSecretTv.setEnabled(!isReady);
      }
    });
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(SettingsActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });

    initView();
  }

  private void initView() {
    initToolbar();
    initFinishButton();

    apiKeyTv.setText(credentialsPreferenceManager.getApiKey());
    apiSecretTv.setText(credentialsPreferenceManager.getApiSecret());

    apiKeyTv.setEnabled(!orchextra.isReady());
    apiSecretTv.setEnabled(!orchextra.isReady());
  }

  private void initFinishButton() {

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

  public static void open(Context context) {
    Intent intent = new Intent(context, SettingsActivity.class);
    context.startActivity(intent);
  }
}
