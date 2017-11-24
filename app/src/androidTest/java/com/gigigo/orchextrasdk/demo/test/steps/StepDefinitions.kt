package com.gigigo.orchextrasdk.demo.test.steps


import android.app.Activity
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import com.gigigo.orchextrasdk.demo.test.screen.LoginScreen
import com.gigigo.orchextrasdk.demo.test.screen.ScannerScreen
import com.gigigo.orchextrasdk.demo.test.util.ActivityFinisher
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity
import cucumber.api.CucumberOptions
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import junit.framework.Assert.assertNotNull
import org.junit.Rule

@CucumberOptions(features = arrayOf("features"))
class StepDefinitions {

  @Rule
  private val activityTestRule = ActivityTestRule(LoginActivity::class.java,
      false, false)

  private var activity: Activity? = null
  private val loginScreen = LoginScreen()
  private val scannerScreen = ScannerScreen()

  @Before
  fun setup() {
    activityTestRule.launchActivity(Intent())
    activity = activityTestRule.activity

    HelperSteps.grantLocationPermissions()
  }

  @After
  fun tearDown() {
    ActivityFinisher.finishOpenActivities()
  }

  @Given("^I have a login view")
  fun I_have_a_login_view() {
    assertNotNull(activity)
  }

  @When("^I input apiKey (\\S+)$")
  fun I_input_apiKey(apiKey: String) {
    loginScreen.apiKeyEditText {
      clearText()
      typeText(apiKey)
    }
    Espresso.closeSoftKeyboard()
  }

  @When("^I input apiSecret (\\S+)\$")
  fun I_input_apiSecret(apiSecret: String) {
    loginScreen.apiSecretEditText {
      clearText()
      typeText(apiSecret)
    }
    Espresso.closeSoftKeyboard()
  }

  @When("^I press start button$")
  fun I_press_start_button() {
    try {
      loginScreen.startButton.click()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Then("^I should see auth error$")
  fun I_should_see_auth_error() {
    loginScreen {
      idle(3000L)
      errorTextView.isDisplayed()
    }
  }

  @Then("^I should see scanner view$")
  fun I_should_see_scanner_view() {
    scannerScreen {
      idle(3000L)
      oxScannerButton.isVisible()
    }
  }
}
