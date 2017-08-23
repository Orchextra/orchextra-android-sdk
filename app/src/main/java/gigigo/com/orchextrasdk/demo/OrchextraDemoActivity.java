package gigigo.com.orchextrasdk.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import gigigo.com.orchextrasdk.R;
import gigigo.com.orchextrasdk.demo.scanner.ScannerFragment;
import gigigo.com.orchextrasdk.demo.triggerlog.TriggerLogFragment;

public class OrchextraDemoActivity extends AppCompatActivity {

  ScannerFragment scannerFragment;
  TriggerLogFragment triggerLogFragment;

  BottomNavigationView navigation;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_orchextra_demo);

    initView();

    scannerFragment = ScannerFragment.newInstance();
    triggerLogFragment = TriggerLogFragment.newInstance();

    if (savedInstanceState == null) {
      navigation.setSelectedItemId(R.id.navigation_scanner);
    }
  }

  private void initView() {

    initToolbar();

    navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {

          @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              case R.id.navigation_scanner:
                showView(scannerFragment);
                return true;
              case R.id.navigation_geofences:
                return true;
              case R.id.navigation_triggers_log:
                showView(triggerLogFragment);
                return true;
            }
            return false;
          }
        });
  }

  private void initToolbar() {

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    //toolbar.setNavigationOnClickListener({ onBackPressed() })
  }

  private void showView(Fragment fragment) {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .commit();
  }

  public static void open(Context context) {
    Intent intent = new Intent(context, OrchextraDemoActivity.class);
    context.startActivity(intent);
  }
}
