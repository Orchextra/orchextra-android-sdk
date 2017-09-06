/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextrasdk.pages;

import android.content.Context;
import android.support.test.espresso.ViewInteraction;
import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver;
import com.gigigo.orchextrasdk.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class DemoPage extends BasePage {

  public DemoPage(Context context) {
    super(context);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public ScannerPage openScannerDemoView() {
    ViewInteraction bottomNavigationItemView =
        onView(allOf(withId(R.id.navigation_scanner), isDisplayed()));
    bottomNavigationItemView.perform(click());

    return new ScannerPage(context);
  }

  public GeofencesPage openGeofencesDemoView() {
    ViewInteraction bottomNavigationItemView =
        onView(allOf(withId(R.id.navigation_geofences), isDisplayed()));
    bottomNavigationItemView.perform(click());

    return new GeofencesPage(context);
  }

  public TriggerLogPage openTriggerLogDemoView() {
    ViewInteraction bottomNavigationItemView =
        onView(allOf(withId(R.id.navigation_triggers_log), isDisplayed()));
    bottomNavigationItemView.perform(click());

    return new TriggerLogPage(context);
  }

  public SettingsPage openSettingsView() {
    ViewInteraction actionMenuItemView = onView(allOf(withId(R.id.action_settings), isDisplayed()));
    actionMenuItemView.perform(click());

    return new SettingsPage(context);
  }

  public WebViewPage detectQRCodeWithOpenWebViewActionAssociated() {

    context.sendBroadcast(TriggerBroadcastReceiver.Navigator.getTriggerIntent(
        new Trigger(TriggerType.QR, "google", null, null, null, null, null, null, null, null)));

    return new WebViewPage(context);
  }
}
