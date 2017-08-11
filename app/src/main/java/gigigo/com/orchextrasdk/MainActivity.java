package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.OrchextraStatusListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.scanner.OxScannerImp;

public class MainActivity extends AppCompatActivity {

  private static String TAG = "MainActivity";
  public static String API_KEY = "122f51a9f80a93270dfbd61b027155936031bba9";
  public static String API_SECRET = "54b0294038ae8118db6d996d4db4e082aa8447df";
  private Orchextra orchextra;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    orchextra = Orchextra.INSTANCE;
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
            .show();
      }
    });

    initView();
  }

  private void initView() {
    Button initButton = (Button) findViewById(R.id.init_button);
    initButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        orchextra.init(MainActivity.this, API_KEY, API_SECRET, orchextraStatusListener);
      }
    });

    Button browserButton = (Button) findViewById(R.id.browser_button);
    browserButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.openBrowser("https://www.google.es");
        } else {
          Toast.makeText(MainActivity.this, "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    Button webviewButton = (Button) findViewById(R.id.webview_button);
    webviewButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.openWebView("https://www.google.es");
        } else {
          Toast.makeText(MainActivity.this, "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });

    Button scannerButton = (Button) findViewById(R.id.scanner_button);
    scannerButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (orchextra.isReady()) {
          orchextra.openScanner();
        } else {
          Toast.makeText(MainActivity.this, "SDK sin inicializar", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private OrchextraStatusListener orchextraStatusListener = new OrchextraStatusListener() {
    @Override public void onStatusChange(boolean isReady) {
      if (isReady) {
        orchextra.getTriggerManager().setScanner(OxScannerImp.Factory.create());
        Toast.makeText(MainActivity.this, "SDK ready", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(MainActivity.this, "SDK finished", Toast.LENGTH_SHORT).show();
      }
    }
  };
}
