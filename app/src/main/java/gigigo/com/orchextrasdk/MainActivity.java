package gigigo.com.orchextrasdk;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.CustomSchemeReceiver;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.ui.webview.OxWebViewActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //orchextraSDK
        getViews();
        setListeners();

    }


    //region Orchextra
    private boolean isRunning = false;
    private Button button, button2, button3, button4, button5, button6, button7, button8;
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
        button8 = (Button) findViewById(R.id.button8);

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
        button8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

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
            OxWebViewActivity.open(this, App.GIGIGO_URL, false);
            Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    if (scheme.contains(App.CUSTOM_SCHEME)) {
                        String url = scheme.replace(App.CUSTOM_SCHEME, "");
                        OxWebViewActivity.navigateUrl(url);
                    }
                }
            });
        }
        //endregion

        //region orchextraWebview in app activity
        if (v.getId() == R.id.button5) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("URL", App.GIGIGO_URL);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
        }
        //endregion

        //region Segmentation
        if (v.getId() == R.id.button6) {
            List<String> lst_tags, lst_bu;
            //all tags/BU or Custom fields must be exist in project dashboard configuration
            //only the matching Tags/BU/CF will be setted on server, you can check it on configuration json response
            lst_tags = Arrays.asList("tagTest", "tagTest1");
            lst_bu = Arrays.asList("BuTest", "BuTest1");
            //region Segmentation DEVICE
            //this set tag for the device
            Orchextra.setDeviceTags(lst_tags);
            Orchextra.setDeviceBusinessUnits(lst_bu);
            //endregion
            //region Segmentation CRMUser
            //if you have a binded Crm User you can set tags/bu/cf
            Orchextra.setUserTags(lst_tags);
            Orchextra.setUserBusinessUnits(lst_bu);

            //Custom Fields, like tags and bu is mandatory that the CF exist on dashboard
            Map<String, String> nameValue = new HashMap<String, String>();
            nameValue.put("name", "yourName");
            Orchextra.setUserCustomFields(nameValue);
            //endregion
            //this request config and save the segmentation data on server side
            Orchextra.commitConfiguration();

            //region Clear segmentation
            //For clear all segmentation data
            //Orchextra.clearDeviceBusinessUnits();
            //Orchextra.clearDeviceTags();

            //Orchextra.clearUserTags();
            //Orchextra.clearUserBusinessUnits();

            //Orchextra.clearUserCustomFields();
            //request configuration for save in server side
            //Orchextra.commitConfiguration();
            //endregion
        }
        //endregion

        //region unBindUser
        if (v.getId() == R.id.button7) {
            //for reset the CRMUser
            Orchextra.unBindUser();
            //rin?
            Orchextra.commitConfiguration();
        }
        //endregion

        //region BindUser
        if (v.getId() == R.id.button8) {

            // CRM User
            String CRM_ID = getUniqueCRMID();
            Orchextra.bindUser(new CrmUser(CRM_ID,
                    new GregorianCalendar(1981, Calendar.MAY, 31),
                    CrmUser.Gender.GenderMale));

            Orchextra.commitConfiguration();
        }
        //endregion
    }

    private String getUniqueCRMID() {
        String secureAndroidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String serialNumber = Build.SERIAL;
        String deviceToken = secureAndroidId + BuildConfig.APPLICATION_ID + serialNumber;
        return deviceToken;
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
        //UpdateConfigWrapper ONly when Start
        UpdateConfigWrapper updater = new UpdateConfigWrapper(this);
        updater.createUpdateConfigurationByTime(1000, 10000);
        enablerUpdateConfigReBootService(this.getApplication(), true);

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
        enablerUpdateConfigReBootService(this.getApplication(), true);
    }


    private void enablerUpdateConfigReBootService(Application application, boolean bState) {
        int componentEnabledState;
        if (!bState) {
            componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        } else {
            componentEnabledState = PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }
        ComponentName component = new ComponentName(application, UpdateConfigReceiver.class);
        application.getPackageManager().setComponentEnabledSetting(component, componentEnabledState, PackageManager.DONT_KILL_APP);
    }

    //endregion
}
