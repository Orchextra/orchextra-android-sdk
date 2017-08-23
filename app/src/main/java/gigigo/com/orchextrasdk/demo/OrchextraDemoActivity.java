package gigigo.com.orchextrasdk.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import gigigo.com.orchextrasdk.R;
import gigigo.com.orchextrasdk.demo.scanner.ScannerFragment;

public class OrchextraDemoActivity extends AppCompatActivity {

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_home:
              //mTextMessage.setText(R.string.title_home);
              return true;
            case R.id.navigation_dashboard:
              //mTextMessage.setText(R.string.title_dashboard);
              return true;
            case R.id.navigation_notifications:
              //mTextMessage.setText(R.string.title_notifications);
              return true;
          }
          return false;
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_orchextra_demo);

    initView();

    if (findViewById(R.id.fragment_container) != null) {
      if (savedInstanceState != null) {
        return;
      }

      ScannerFragment scannerFragment = ScannerFragment.newInstance();
      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, scannerFragment)
          .commit();
    }
  }

  private void initView() {

    initToolbar();

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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

  public static void open(Context context) {
    Intent intent = new Intent(context, OrchextraDemoActivity.class);
    context.startActivity(intent);
  }
}
