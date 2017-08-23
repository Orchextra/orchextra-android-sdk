package gigigo.com.orchextrasdk.demo.triggerlog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import gigigo.com.orchextrasdk.R;

public class TriggerLogFragment extends Fragment {

  public TriggerLogFragment() {
  }

  public static TriggerLogFragment newInstance() {
    return new TriggerLogFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_trigger_log, container, false);
  }
}
