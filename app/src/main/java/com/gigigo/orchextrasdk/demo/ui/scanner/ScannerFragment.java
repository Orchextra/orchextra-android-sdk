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

package com.gigigo.orchextrasdk.demo.ui.scanner;

import android.Manifest;
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
import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextra.core.domain.exceptions.OxException;
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver;
import com.gigigo.orchextra.core.utils.PermissionsActivity;
import com.gigigo.orchextra.imagerecognizer.OxImageRecognizerImp;
import com.gigigo.orchextra.scanner.OxScannerImp;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.scanner.custom.CustomScannerImp;
import com.gigigo.orchextrasdk.demo.utils.integration.Ox3ManagerImp;
import com.gigigo.orchextrasdk.demo.utils.integration.OxManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class ScannerFragment extends Fragment {

  private static final String TAG = "ScannerFragment";
  private Button oxScannerButton;
  private Button oxCustomScannerButton;
  private Button oxImageRecognitionButton;
  private Button oxScanCodeButton;
  private Button dispatchQRbutton;
  private Button requestLocationButton;
  private OxManager oxManager;

  public ScannerFragment() {
  }

  public static ScannerFragment newInstance() {
    return new ScannerFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_scanner, container, false);

    oxScannerButton = view.findViewById(R.id.ox_scanner_button);
    oxCustomScannerButton = view.findViewById(R.id.ox_custom_scanner_button);
    oxImageRecognitionButton = view.findViewById(R.id.ox_image_recognition_button);
    oxScanCodeButton = view.findViewById(R.id.ox_scan_code_button);
    dispatchQRbutton = view.findViewById(R.id.dispatch_qr_button);
    requestLocationButton = view.findViewById(R.id.request_location_button);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initOrchextra();
    initView();
  }

  private void initOrchextra() {
    oxManager = new Ox3ManagerImp();
    oxManager.setErrorListener(new OxManager.ErrorListener() {
      @Override public void onError(@NonNull String error) {
        Log.e(TAG, error);
        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initView() {

    final Orchextra orchextra = Orchextra.INSTANCE;

    oxScannerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.getTriggerManager()
              .setScanner(OxScannerImp.Factory.create(getActivity().getApplication()));
          orchextra.openScanner();
        } else {
          Toast.makeText(getContext(), "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    oxScanCodeButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.getTriggerManager()
              .setScanner(OxScannerImp.Factory.create(getActivity().getApplication()));
          orchextra.scanCode(new Function1<String, Unit>() {
            @Override public Unit invoke(String customSchema) {

              Toast.makeText(getContext(), "Scanner: " + customSchema, Toast.LENGTH_SHORT).show();
              return null;
            }
          });
        } else {
          Toast.makeText(getContext(), "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    oxCustomScannerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.getTriggerManager()
              .setScanner(CustomScannerImp.Factory.create(getActivity().getApplication()));
          orchextra.openScanner();
        } else {
          Toast.makeText(getContext(), "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    oxImageRecognitionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.getTriggerManager()
              .setImageRecognizer(OxImageRecognizerImp.Factory.create(getActivity()));
          orchextra.openImageRecognition();
        } else {
          Toast.makeText(getContext(), "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    dispatchQRbutton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Trigger qrTrigger = TriggerType.QR.withValue("qrcustomaction");
        getActivity().sendBroadcast(
            TriggerBroadcastReceiver.Navigator.getTriggerIntent(getContext(), qrTrigger));
      }
    });

    requestLocationButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        PermissionsActivity.Navigator.open(getContext(), Manifest.permission.ACCESS_FINE_LOCATION,
            new Function0<Unit>() {
              @Override public Unit invoke() {
                return null;
              }
            }, new Function1<OxException, Unit>() {
              @Override public Unit invoke(OxException e) {
                return null;
              }
            });
      }
    });
  }

  @Override public void onDestroyView() {
    oxManager.removeListeners();
    super.onDestroyView();
  }
}
