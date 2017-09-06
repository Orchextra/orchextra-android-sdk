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

package gigigo.com.orchextrasdk.demo.ui.scanner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import gigigo.com.orchextrasdk.R;

public class ScannerFragment extends Fragment {

  private static final String TAG = "ScannerFragment";
  private Orchextra orchextra;
  private Button oxScannerButton;
  private Button oxImageRecognitionButton;

  public ScannerFragment() {
  }

  public static ScannerFragment newInstance() {
    return new ScannerFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_scanner, container, false);

    oxScannerButton = (Button) view.findViewById(R.id.ox_scanner_button);
    oxImageRecognitionButton = (Button) view.findViewById(R.id.ox_image_recognition_button);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initOrchextra();
    initView();
  }

  private void initOrchextra() {
    orchextra = Orchextra.INSTANCE;
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initView() {

    oxScannerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.openScanner();
        } else {
          Toast.makeText(getContext(), "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    oxImageRecognitionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Toast.makeText(getContext(), "Operation is not implemented", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
