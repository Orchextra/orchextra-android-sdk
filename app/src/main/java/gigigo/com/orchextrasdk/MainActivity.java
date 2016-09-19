package gigigo.com.orchextrasdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*import com.appoxee.Appoxee;
import com.appoxee.AppoxeeObserver;
import com.appoxee.asyncs.initAsync;*/
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.domain.model.StringValueEnum;
import com.gigigo.orchextra.ui.webview.OxWebViewActivity;
//import com.karumi.dexter.asv.MyActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("APP", "Hello MainActivity, start onCreate");

        //orchextraSDK
        getViews();
        setListeners();

        //appoxeeSDK
        startAppoxee();
    }

    //region Appoxee
    private final static String APPOXEE_SDK_KEY = "575e7877b16df0.22390647";
    private final static String APPOXEE_SDK_SECRET = "575e7877b16f48.87387869";
    private static final String TAG = "MainActivity";
    private static final String SOME_UNIQUE_USER_IDENTIFIER = "example";
    private static final String SOME_CUSTOM_FIELD = "custom_field";
    ExecutorService threadPool = Executors.newSingleThreadExecutor();

    private void startAppoxee() {
      /*  new initAsync(this,
                APPOXEE_SDK_KEY,
                APPOXEE_SDK_SECRET,
                MainActivity.class.getName(), true, appoxeeCallbacksObserver)
                .execute();
*/
    }

    //we'll wait for appoxee to complete registration before using the API
   /* private AppoxeeObserver appoxeeCallbacksObserver = new AppoxeeObserver() {
        @Override
        public void onRegistrationCompleted() {
            Log.d(TAG, "appoxee is ready");

            //using a background thread, because API method invoke network operations directly
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting device alias
                    boolean success = Appoxee.setDeviceAlias(SOME_UNIQUE_USER_IDENTIFIER);
                    Log.d(TAG, "alias had been set: " + success);

                    //reading device alias value
                    String alias = Appoxee.getDeviceAlias();
                    Log.d(TAG, "alias: " + alias);
                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    //setting a custom fiels
                    boolean success = Appoxee.setCustomField(SOME_CUSTOM_FIELD, "ok");
                    Log.d(TAG, "custom field set success: " + success);

                    ArrayList<String> fieldArray = new ArrayList<String>();
                    fieldArray.add(SOME_CUSTOM_FIELD);
                    //reading custom field value
                    List<String> fieldValue = Appoxee.getCustomFieldsValues(fieldArray);
                    if (fieldValue != null && fieldValue.size() > 0) {
                        Log.d(TAG, "field value: " + fieldValue.get(0));
                    } else {
                        Log.w(TAG, "couldn't get field value");
                    }

                }
            });

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    String[] tagArray = {"example_tag"};
                    boolean success = Appoxee.addTagsToDevice(new ArrayList<String>(Arrays.asList(tagArray)));
                    Log.d(TAG, "custom field set success: " + success);

                    List<String> tags = Appoxee.getTagList();
                    Log.d(TAG, "tags list: " + tags);
                }
            });
        }

        @Override
        public void onRegistrationFailure() {
            Log.d(TAG, "appoxee registration failed");
        }

        @Override
        public void onGeoRegistrationFailure() {
            //for future support
        }

        @Override
        public void onMessagesUpdateCompleted() {
            //for custom inbox
        }
    };
*/
    //endregion

    //region Orchextra
    private boolean isRunning = false;
    private Button button, button2, button3, button4, button5, button6, button7;
    private TextView statusText;

    //region getViews/Buttons onClick
    private void getViews() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        statusText = (TextView) findViewById(R.id.statusText);
    }

    private void setListeners() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final String TEST_STREAM_URL = "http://research.gigigo.com";
        final String CUSTOM_SCHEME = "webview://";
        if (v.getId() == R.id.button) {
            Orchextra.startImageRecognition();
        }
        if (v.getId() == R.id.button2) {
            Orchextra.startScannerActivity();
        }
        if (v.getId() == R.id.button3) {
            if (isRunning) {
                stopOrchextra();
            } else {
                startOrchextra();
            }
        }
        //region orchextraWebview

        if (v.getId() == R.id.button4) {
            OxWebViewActivity.open(this, TEST_STREAM_URL, false);
            Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    if (scheme.contains(CUSTOM_SCHEME)) {
                        String url = scheme.replace(CUSTOM_SCHEME, "");
                        OxWebViewActivity.navigateUrl(url);
                    }
                }
            });
        }
        //endregion

        //region orchextraWebview in app activity
        if (v.getId() == R.id.button5) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", TEST_STREAM_URL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        //endregion

        //region test Tags/Bu/CustomFields
        if (v.getId() == R.id.button6) {
          /*  Log.i("", "Device Tags: " + Orchextra.getDeviceTags().toString());
            Log.i("", "Device BU: " + Orchextra.getDeviceBusinessUnits().toString());
            //---------------------------------------------------------------------
            Log.i("", "User CustomFields: " + Orchextra.getUserCustomFields().toString());
            Log.i("", "User BU: " + Orchextra.getUserBusinessUnits().toString());
            Log.i("", "User Tags: " + Orchextra.getUserTags().toString());*/
        }

        if (v.getId() == R.id.button7) {

            //region test
           /*
           =============================
           Datos Válidos para Tags:
           Distancia
           Distancia::10
           Distancia::20
           Distancia::30



           Color
           Color::rojominuscula
           =================================
           Datos Válidos para BU:
           asvbu
           EtiquetaBU
           Establecimiento
 BUCSE, BUCSE PR, BU Old
           Pruebas Con Crm(seteado) y Device
           Caso 1:
           Seteo y reseteo tags/bu válidas
           Caso 2:
           Seteo y reseteo tags/bu palabra correcta, xo mal mayusculas y minusculas
           Caso 3:
           Igual que Caso 1 xo con value ::10
           Caso 4:
           Igual que Caso 2 xo con value ::10
           Caso 5:
           Seteo y reseteo tags/bu inválidas, añadiendo caracteres "especiales" _b _s
           Caso 6:
           Seteo y reseteo tags/bu inválidas, añadiendo caracteres "especiales" _b _s y poniendo en tags set de clave Bu y viceversa
           Caso 7:
           Tanto en Bu cmo tags, establecer varias claves y despues quitar una de ellas a ver como se comporta el siguiente retrivede config*/
//endregion
            //region for
           /*
            List<String> lst_tags, lst_bu;


            lst_tags = Arrays.asList();
            lst_bu = Arrays.asList();

            for (int i = 1; i < 8; i++) {

                Log.i("", "Caso=============================> " + i);
                Orchextra.setDeviceTags(lst_tags);
                Orchextra.setDeviceBusinessUnits(lst_bu);

                Orchextra.setUserTags(lst_tags);
                Orchextra.setUserBusinessUnits(lst_bu);
                //request config
                Orchextra.start();
                //check
                if (lst_bu.equals(Orchextra.getUserBusinessUnits()))
                    Log.i("User BU caso " + i + " :)", "");
                else
                    Log.i("", "User BU caso " + i + " :(");

                if (lst_tags.equals(Orchextra.getUserTags()))
                    Log.i("", "User TAGS caso " + i + " :)");
                else
                    Log.i("", "User TAGS caso " + i + " :(");

                if (lst_bu.equals(Orchextra.getDeviceBusinessUnits()))
                    Log.i("", "Device BU caso " + i + " :)");
                else
                    Log.i("", "Device BU caso " + i + " :(");

                if (lst_tags.equals(Orchextra.getDeviceTags()))
                    Log.i("", "Device TAGS caso " + i + " :)");
                else
                    Log.i("", "Device TAGS caso " + i + " :(");
                lst_tags = getTestCaseList(t_TAG, i);
                lst_bu = getTestCaseList(t_BU, i);

            }
*/
            //pruebas de CRMUser
            // comprobar que pasa si se borrael CRMUser(se puede?)


            //endregion
/*
            Lst_tags = Arrays.asList("", "", "");
            Lst_bu = Arrays.asList("BUCSE");//,"BUCSE PR","BU Old");

            Orchextra.setDeviceTags(Lst_tags);
            Orchextra.start();
*/
            // Orchextra.setUserBusinessUnits(Lst_tags);
            // Orchextra.setDeviceBusinessUnits(Lst_bu);
            // Orchextra.start();

            //Custom Fields
            /*
            Map<String,String> nameValue = new HashMap<String,String>();
            nameValue.put("name","Alberto");
            Orchextra.setUserCustomFields(nameValue);
            Orchextra.start();
            Orchextra.setUserTags(Lst_tags);
            Orchextra.start();
            Orchextra.setUserBusinessUnits(Lst_bu);
            Orchextra.start();
*/
/*
            Orchextra.start();




*/
            /*
            //clear all
            Orchextra.clearDeviceBusinessUnits();
            Orchextra.clearDeviceTags();

            Orchextra.clearUserTags();
            Orchextra.clearUserBusinessUnits();

            Orchextra.clearUserCustomFields();

*/


        }
    }

    //region for testing tags/BU/Custom fields
    List<String> Lst_tags, Lst_bu;
    String t_BU = "BU";
    String t_TAG = "TAG";
    String t_CF = "CF";
    //region values 4 Tags
    String tag_valid1 = "bottle";
    String tag_valid2 = "color";
    String tag_valid3 = "Edad";

    String tag_upperLowwerInvalid1 = "Color";
    String tag_upperLowwerInvalid2 = "madrid";
    String tag_upperLowwerInvalid3 = "edad";

    String tagWvalue_valid1 = "color::red";
    String tagWvalue_valid2 = "color::Red";
    String tagWvalue_valid3 = "color::yellow";

    String tagWvalue_upperLowwerInvalid1 = "color::ReD";
    String tagWvalue_upperLowwerInvalid2 = "Color::Yellow";
    String tagWvalue_upperLowwerInvalid3 = "edad::peterpan";

    String tag_invalid1 = "colorete";
    String tag_invalid2 = "distancilla";
    String tag_invalid3 = "edadude";

    String tag_prefix_invalid1 = "colorete_b";
    String tag_prefix_invalid2 = "_b_sdistancilla";
    String tag_prefix_invalid3 = "_bedadude_s";
    //endregion

    //region values 4 BU
    String bu_valid1 = "BUCSE";
    String bu_valid2 = "BUCSE PR";
    String bu_valid3 = "BU Old";

    String bu_upperLowwerInvalid1 = "buCSEBUCSE";
    String bu_upperLowwerInvalid2 = "buCSE PR";
    String bu_upperLowwerInvalid3 = "bu Old";

    String bu_invalid1 = "alberBu";
    String bu_invalid2 = "labelbu";
    String bu_invalid3 = "estaVlecimiento";

    String bu_prefix_invalid1 = "_balberBu";
    String bu_prefix_invalid2 = "_slabelbu";
    String bu_prefix_invalid3 = "_sestaVlecimiento";
    //endregion
   /* Pruebas Con Crm(seteado) y Device
    caso 0:
    set vacio
    Caso 1:
    Seteo y reseteo tags/bu válidas
    Caso 2:
    Seteo y reseteo tags/bu palabra correcta, xo mal mayusculas y minusculas
    Caso 3:(tags)
    Igual que Caso 1 xo con value ::10
    Caso 4:(tags)
    Igual que Caso 2 xo con value ::10
    Caso 5:
    Seteo y reseteo tags/bu inválidas, añadiendo caracteres "especiales" _b _s
    Caso 6:
    Seteo y reseteo tags/bu inválidas, añadiendo caracteres "especiales" _b _s y poniendo en tags set de clave Bu y viceversa
    Caso 7:
    Tanto en Bu cmo tags, establecer varias claves y despues quitar una de ellas a ver como se comporta el siguiente retrivede config
    Caso 8:
    enviar varias veces el mismo valor válido

*/

    public List<String> getTestCaseList(String type, int CaseNumber) {
        if (type.equals(t_BU)) {
            switch (CaseNumber) {
                case 1:
                    return Arrays.asList(bu_valid1, bu_valid2, bu_valid3);
                case 2:
                    return Arrays.asList(bu_upperLowwerInvalid1, bu_upperLowwerInvalid2, bu_upperLowwerInvalid3);
                case 3://valid
                    return Arrays.asList(bu_valid1, bu_valid2, bu_valid3);
                case 4://valid
                    return Arrays.asList(bu_valid1, bu_valid2, bu_valid3);
                case 5:
                    return Arrays.asList(bu_prefix_invalid1, bu_prefix_invalid2, bu_prefix_invalid3);
                case 6:
                    return Arrays.asList(tag_prefix_invalid1, tag_prefix_invalid2, tag_prefix_invalid3);
                case 7:
                    return Arrays.asList(bu_valid1, bu_valid2);
            }
        }
        if (type.equals(t_TAG)) {
            switch (CaseNumber) {
                case 1:
                    return Arrays.asList(tag_valid1, tag_valid2, tag_valid3);
                case 2:
                    return Arrays.asList(tag_upperLowwerInvalid1, tag_upperLowwerInvalid2, tag_upperLowwerInvalid3);
                case 3:
                    return Arrays.asList(tagWvalue_valid1, tagWvalue_valid2, tagWvalue_valid3);
                case 4:
                    return Arrays.asList(tagWvalue_upperLowwerInvalid1, tagWvalue_upperLowwerInvalid2, tagWvalue_upperLowwerInvalid3);
                case 5:
                    return Arrays.asList(tag_prefix_invalid1, tag_prefix_invalid2, tag_prefix_invalid3);
                case 6:
                    return Arrays.asList(bu_prefix_invalid1, bu_prefix_invalid2, bu_prefix_invalid3);
                case 7:
                    return Arrays.asList(tag_valid1, tag_valid2);

            }
        }

        if (type.equals(t_CF)) {
            switch (CaseNumber) {
                case 1:
                    return Arrays.asList("asvbu", "etiquetabu", "loquemeinventeeigulnocuela::23");

            }
        }

        return null;
    }


    //endregion
    private void startOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.start();
            }
        });

        isRunning = true;
        button3.setText(R.string.ox_stop_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_running)));
    }

    private void stopOrchextra() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Orchextra.stop();
            }
        });
        isRunning = false;
        button3.setText(R.string.ox_start_orchextra);
        statusText.setText(getString(R.string.status_text, getString(R.string.status_stoped)));
    }
}
