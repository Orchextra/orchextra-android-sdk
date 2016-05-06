package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.Orchextra;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    Orchextra.setUser(new ORCUser("123456789",
        new GregorianCalendar(1990, 10, 29), //any Birth date as a calendar instance
        ORCUser.Gender.ORCGenderMale, //ORCGenderMale or ORCGenderFemale Enum
        new ArrayList<>(Arrays.asList("keyword1", "keyword2"))));
  }


}
