package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


  private boolean isRunning = false;

  private Button button3;
  private TextView statusText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d("APP", "Hello MainActivity, start onCreate");

    setContentView(R.layout.activity_main);

    Orchextra.setUser(new ORCUser("123456789",
        new GregorianCalendar(1990, 10, 29), //any Birth date as a calendar instance
        ORCUser.Gender.ORCGenderMale, //ORCGenderMale or ORCGenderFemale Enum
        new ArrayList<>(Arrays.asList("keyword1", "keyword2"))));

    Orchextra.setImageRecognitionModule(new ImageRecognitionVuforiaImpl());

    Button button = (Button) findViewById(R.id.button);
    Button button2 = (Button) findViewById(R.id.button2);
    button.setOnClickListener(this);
    button2.setOnClickListener(this);

    Log.d("APP", "Hello MainActivity, end onCreate");

    button3 = (Button) findViewById(R.id.button3);
    button3.setOnClickListener(this);

    statusText = (TextView) findViewById(R.id.statusText);

    startOrchextra();
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.button){
      Orchextra.startImageRecognition();
    }
    if (v.getId() == R.id.button2){
      Orchextra.startScannerActivity();
    }
    if (v.getId() == R.id.button3) {
      if (isRunning) {
        stopOrchextra();
      } else {
        startOrchextra();
      }
    }
  }

  private void startOrchextra() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.start(App.API_KEY, App.API_SECRET);
      }
    });

    isRunning = true;
    button3.setText(R.string.ox_stop_orchextra);
    statusText.setText(getString(R.string.status_text, getString(R.string.status_running)));
  }

  private void stopOrchextra() {
    new Handler().post(new Runnable() {
      @Override public void run() {
        Orchextra.sdkStop();
      }
    });
    isRunning = false;
    button3.setText(R.string.ox_start_orchextra);
    statusText.setText(getString(R.string.status_text, getString(R.string.status_stoped)));
  }
}
