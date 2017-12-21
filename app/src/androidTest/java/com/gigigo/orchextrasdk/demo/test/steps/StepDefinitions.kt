package com.gigigo.orchextrasdk.demo.test.steps


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import com.gigigo.orchextra.core.domain.actions.ActionHandlerServiceExecutor
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.ActionType
import com.gigigo.orchextra.core.domain.entities.ActionType.NOTHING
import com.gigigo.orchextra.core.domain.entities.ActionType.NOTIFICATION
import com.gigigo.orchextra.core.domain.entities.Notification
import com.gigigo.orchextrasdk.demo.test.screen.LoginScreen
import com.gigigo.orchextrasdk.demo.test.screen.WebViewScreen
import com.gigigo.orchextrasdk.demo.test.util.ActivityFinisher
import com.gigigo.orchextrasdk.demo.ui.login.LoginActivity
import cucumber.api.CucumberOptions
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.junit.Rule

@CucumberOptions(features = arrayOf("features"))
class StepDefinitions {

  private val TEST_API_KEY = "34a4654b9804eab82aae05b2a5f949eb2a9f412c"
  private val TEST_API_SECRET = "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3"

  @Rule
  private val activityTestRule = ActivityTestRule(LoginActivity::class.java,
      false, false)

  private var activity: Activity? = null
  private val loginScreen = LoginScreen()
  private val webViewScreen = WebViewScreen()

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

  @Given("^The app logged$")
  fun The_app_logged() {

    loginScreen {
      apiKeyEditText {
        clearText()
        typeText(TEST_API_KEY)
      }

      Espresso.closeSoftKeyboard()

      apiSecretEditText {
        clearText()
        typeText(TEST_API_SECRET)
      }

      Espresso.closeSoftKeyboard()
      startButton.click()
      idle(3000L)
    }
  }

  @When("^The app get a \"(.+)\" action with url \"(\\S+)\"$")
  fun simulate_get_action_with_url(action: String, url: String) {
    val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(activity as Context)
    actionHandlerServiceExecutor.execute(Action(
        type = ActionType.fromOxType(action),
        url = url))
  }

  @When("^The app get a \"(.+)\" action$")
  fun simulate_get_action(action: String) {
    val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(activity as Context)
    actionHandlerServiceExecutor.execute(Action(type = ActionType.fromOxType(action)))
  }

  @When("^The app get any action with notification title: \"(.+)\" and body: \"(.+)\"$")
  fun simulate_get_action_with_notification(title: String, body: String) {
    val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(activity as Context)
    actionHandlerServiceExecutor.execute(Action(
        type = NOTHING,
        notification = Notification(title, body)))
  }

  @When("^The app get a notification action with notification title: \"(.+)\" and body: \"(.+)\"$")
  fun simulate_get_notification_action(title: String, body: String) {
    val actionHandlerServiceExecutor = ActionHandlerServiceExecutor.create(activity as Context)
    actionHandlerServiceExecutor.execute(Action(
        type = NOTIFICATION,
        notification = Notification(title, body)))
  }

  @Then("^I should see a webview with title: \"(\\S+)\"$")
  fun show_webview_title(title: String) {
    webViewScreen {
      idle(3000L)
      // TODO check tittle
      toolbar.isVisible()
    }
  }

  @Then("^I should see the browser$")
  fun open_browser() {
    // TODO browser intent
  }

  @Then("^I should see the Deep link$")
  fun check_deep_link() {
    // TODO check deep link
  }

  @Then("^I should see notification with title: \"(.+)\" and body: \"(.+)\"$")
  fun check_notification(title: String, body: String) {
    // TODO check notification
  }

  @Then("^I should see the scanner$")
  fun check_scanner() {
    // TODO check scanner
  }

  @Then("^I should see nothing$")
  fun check_nothings() {
    // TODO check nothings
  }
}
