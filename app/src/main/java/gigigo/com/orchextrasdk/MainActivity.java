package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

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
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.button){
      Orchextra.startImageRecognition();
    }
    if (v.getId() == R.id.button2){
      Orchextra.startScannerActivity();
    }
  }
}
