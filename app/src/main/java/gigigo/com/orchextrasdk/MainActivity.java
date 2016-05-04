package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Orchextra.setImageRecognitionModule(new ImageRecognitionVuforiaImpl(getApplicationContext()));
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.button){
      Orchextra.startImageRecognition();

    }
  }
}
