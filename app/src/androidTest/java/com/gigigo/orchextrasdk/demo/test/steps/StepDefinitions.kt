package com.gigigo.orchextrasdk.demo.test.steps


import android.app.Activity
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import com.gigigo.orchextrasdk.demo.test.screen.LoginScreen
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
  var activityTestRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

  private var activity: Activity? = null
  private val loginScreen = LoginScreen()

  @Before
  fun setup() {
    activityTestRule.launchActivity(null)
    activity = activityTestRule.activity

    HelperSteps.grantLocationPermissions()
  }

  @After
  fun tearDown() {
    activityTestRule.finishActivity()
  }

  @Given("^I have a LoginActivity")
  fun I_have_a_LoginActivity() {
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

  @When("^I input apiSecret \"(.*?)\"$")
  fun I_input_apiSecret(apiSecret: String) {
    loginScreen.apiSecretEditText {
      clearText()
      typeText(apiSecret)
    }
    Espresso.closeSoftKeyboard()
  }

  @When("^I press start button$")
  fun I_press_start_button() {
    loginScreen.startButton.click()
    HelperSteps.waitForAnimation(1000)
  }

  @Then("^I should (true|false) auth error$")
  fun I_should_see_auth_error(shouldSeeError: Boolean) {
    if (shouldSeeError) {
      loginScreen.errorTextView.isDisplayed()
    } else {
      loginScreen.errorTextView.isNotDisplayed()
    }
  }
}
