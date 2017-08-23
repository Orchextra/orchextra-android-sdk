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

package gigigo.com.orchextrasdk.demo.triggerlog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import gigigo.com.orchextrasdk.R;
import gigigo.com.orchextrasdk.demo.triggerlog.adapter.TriggersAdapter;
import java.util.ArrayList;
import java.util.List;

public class TriggerLogFragment extends Fragment {

  private static final String TAG = "ScannerFragment";
  private Orchextra orchextra;
  private TriggersAdapter triggersAdapter;
  private RecyclerView triggerLogList;

  public TriggerLogFragment() {
  }

  public static TriggerLogFragment newInstance() {
    return new TriggerLogFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_trigger_log, container, false);

    triggerLogList = (RecyclerView) view.findViewById(R.id.trigger_log_list);

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
    initRecyclerView();

    List<Trigger> triggers = new ArrayList<>();

    triggers.add(
        new Trigger(TriggerType.BARCODE, "value 1", null, null, null, null, null, null, null,
            null));
    triggers.add(
        new Trigger(TriggerType.BARCODE, "value 2", null, null, null, null, null, null, null,
            null));
    triggers.add(
        new Trigger(TriggerType.BARCODE, "value 3", null, null, null, null, null, null, null,
            null));
    triggers.add(
        new Trigger(TriggerType.BARCODE, "value 4", null, null, null, null, null, null, null,
            null));

    triggersAdapter.addAll(triggers);
  }

  private void initRecyclerView() {
    triggerLogList.setLayoutManager(new LinearLayoutManager(getContext()));
    triggersAdapter = new TriggersAdapter();
    triggerLogList.setAdapter(triggersAdapter);
  }
}
