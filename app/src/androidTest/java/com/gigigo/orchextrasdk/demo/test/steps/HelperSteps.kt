package com.gigigo.orchextrasdk.demo.test.steps


import android.os.Build
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.espresso.EspressoException
import com.gigigo.orchextrasdk.demo.test.util.SpoonScreenshotAction
import cucumber.api.Scenario
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import java.io.FileInputStream
import java.io.IOException

/**
 * Class containing generic Cucumber test step definitions not related to specific views
 */
class HelperSteps {

  @Before
  fun before(scenario: Scenario) {
    HelperSteps.scenario = scenario
    grantWritePermissionsForScreenshots()
  }

  @After
  fun after() {
    if (HelperSteps.scenario != null && HelperSteps.scenario!!.isFailed) {
      takeScreenshot("failed")
    }
  }

  @Given("^I take a screenshot$")
  fun i_take_a_screenshot() {
    takeScreenshot("screenshot")
  }

  class ScreenshotException internal constructor(message: String) : RuntimeException(
      message), EspressoException {
    companion object {
      private val serialVersionUID = -1247022787790657324L
    }
  }

  companion object {

    var scenario: Scenario? = null
      private set


    private fun grantWritePermissionsForScreenshots() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
          val instrumentation = InstrumentationRegistry.getInstrumentation()
          val appPackage = getTargetContext().packageName
          instrumentation.uiAutomation.executeShellCommand("pm grant " + appPackage
              + " android.permission.READ_EXTERNAL_STORAGE")
          instrumentation.uiAutomation.executeShellCommand("pm grant " + appPackage
              + " android.permission.WRITE_EXTERNAL_STORAGE")
        } catch (e: Exception) {
          throw RuntimeException(
              "Exception while granting external storage access to application apk", e)
        }

      }
    }

    fun grantLocationPermissions() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        try {
          val instrumentation = InstrumentationRegistry.getInstrumentation()
          val appPackage = getTargetContext().packageName
          instrumentation.uiAutomation.executeShellCommand("pm grant " + appPackage
              + " android.permission.ACCESS_FINE_LOCATION")
        } catch (e: Exception) {
          throw RuntimeException(
              "Exception while granting location permission to application apk", e)
        }
      }
    }

    fun waitForAnimation(millis: Long) {
      try {
        Thread.sleep(millis)
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
    }

    /**
     * Take a screenshot of the current activity and embed it in the HTML report
     *
     * @param tag Name of the screenshot to include in the file name
     */
    fun takeScreenshot(tag: String) {
      if (scenario == null) {
        throw ScreenshotException(
            "Error taking screenshot: I'm missing a valid test scenario to attach the screenshot to")
      }
      SpoonScreenshotAction.perform(tag)
      val screenshot = SpoonScreenshotAction.lastScreenshot ?: throw ScreenshotException(
          "Screenshot was not taken correctly, check for failures in screenshot library")
      var screenshotStream: FileInputStream? = null
      try {
        screenshotStream = FileInputStream(screenshot)
        val fileContent = ByteArray(screenshot.length().toInt())
        val readImageBytes = screenshotStream!!.read(
            fileContent) // Read data from input image file into an array of bytes
        if (readImageBytes != -1) {
          scenario!!.embed(fileContent,
              "image/png") // Embed the screenshot in the report under current test step
        }
      } catch (ioe: IOException) {
        throw ScreenshotException("Exception while reading file " + ioe)
      } finally {
        try { // close the streams using close method
          if (screenshotStream != null) {
            screenshotStream.close()
          }
        } catch (ioe: IOException) {

          throw ScreenshotException("Error while closing screenshot stream: " + ioe)
        }

      }
    }
  }
}