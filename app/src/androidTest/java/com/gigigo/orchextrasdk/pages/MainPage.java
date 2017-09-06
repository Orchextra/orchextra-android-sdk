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
import com.gigigo.orchextrasdk.R;
import com.gigigo.orchextrasdk.utils.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

public class MainPage extends BasePage {

  public MainPage(Context context) {
    super(context);

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public ScannerPage startOrchextra() {

    ViewInteraction button = onView(allOf(withId(R.id.start_button),
        TestUtils.childAtPosition(TestUtils.childAtPosition(withId(android.R.id.content), 0), 2),
        isDisplayed()));
    button.perform(click());

    return new ScannerPage(context);
  }

  public SettingsPage openSettingsView() {
    ViewInteraction actionMenuItemView = onView(allOf(withId(R.id.action_settings), isDisplayed()));
    actionMenuItemView.perform(click());

    return new SettingsPage(context);
  }

  public MainPage checkIfStartButtonExist() {
    ViewInteraction button = onView(allOf(withId(R.id.start_button),
        TestUtils.childAtPosition(TestUtils.childAtPosition(withId(android.R.id.content), 0), 2),
        isDisplayed()));
    button.check(matches(isDisplayed()));

    return this;
  }
}
