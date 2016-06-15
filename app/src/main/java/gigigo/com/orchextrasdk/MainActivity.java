package gigigo.com.orchextrasdk;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.ORCUserTag;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("APP", "Hello MainActivity, start onCreate");
Orchextra.setUser();
        setContentView(R.layout.activity_main);
      //  setCrmUser();
        Orchextra.setImageRecognitionModule(new ImageRecognitionVuforiaImpl());

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        Log.d("APP", "Hello MainActivity, end onCreate");
    }

    private void setCrmUser() {
     /*new method setuser for tags*/
        ORCUserTag[] orcUserTagArray = new ORCUserTag[]{
                new ORCUserTag("Pais", "Colombia"),
                new ORCUserTag("::macheo", "espacio espacio"),
                new ORCUserTag("or 1=1", "and 0=0"),
                new ORCUserTag("||  1=1", "&& 0=0"),
                new ORCUserTag("JiYgIDE9MQ==", "JiYgIDE9MQ=="),
                new ORCUserTag("grijander monder6", " 7::1"),
                new ORCUserTag("genero", "macho ta"),
                new ORCUserTag("GEneRo", "/7uijk''=2"),
                new ORCUserTag("yeah 41", "sibarete 34 "),
                new ORCUserTag("12 :3432 dasa", "uoyo"),
                new ORCUserTag("fasa", "teg tags a"),
                new ORCUserTag("fasa", "yanosaleel:nullen el 105"),
                new ORCUserTag("prúébádéa´cénüs", "prúébádéa´cénüs")};

        //todo something like this, maybe we can put this kind of thing always and add the crm_user_id are setted
        String myCrmId = getApplicationContext().getPackageName();
        //
        /*ORCUserTag[] orcUserTagArray = new ORCUserTag[]{
                new ORCUserTag("Pais", "Colombia")};*/
        GregorianCalendar ORCBirthDay = new GregorianCalendar(1990, Calendar.MAY, 29);
        ORCUser orcUser = new ORCUser(myCrmId
                , ORCBirthDay
                , ORCUser.Gender.ORCGenderMale,
                orcUserTagArray);
        Orchextra.setUser(orcUser);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            Orchextra.startImageRecognition();
        }
        if (v.getId() == R.id.button2) {
            Orchextra.startScannerActivity();
        }
    }
}
