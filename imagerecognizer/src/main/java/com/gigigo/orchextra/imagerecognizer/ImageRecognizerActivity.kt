package com.gigigo.orchextra.imagerecognizer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView
import com.gigigo.ggglib.ContextProvider
import com.gigigo.imagerecognitioninterface.ImageRecognitionCredentials
import com.gigigo.orchextra.core.domain.entities.TriggerType
import com.gigigo.orchextra.core.receiver.TriggerBroadcastReceiver
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl

class ImageRecognizerActivity : AppCompatActivity() {

  private lateinit var licenseKeyTv: TextView
  private lateinit var accessKeyTv: TextView
  private lateinit var secretKeyTv: TextView
  private lateinit var codeTv: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_imagerecognizer)

    initViews()

    licenseKeyTv.text = "license key: ${getLicenseKey()}"
    accessKeyTv.text = "access key: ${getClientAccessKey()}"
    secretKeyTv.text = "secret key: ${getClientSecretKey()}"
  }

  private fun initViews() {
    initToolbar()

    licenseKeyTv = findViewById(R.id.imagerecognizer_license_tv) as TextView
    accessKeyTv = findViewById(R.id.imagerecognizer_accessKey_tv) as TextView
    secretKeyTv = findViewById(R.id.imagerecognizer_secretKey_tv) as TextView
    codeTv = findViewById(R.id.imagerecognizer_code_tv) as TextView

    val startButton = findViewById(R.id.start_vuforia_button) as Button
    startButton.setOnClickListener({ startVuforia() })
  }

  private fun initToolbar() {

    val toolbar = findViewById(com.gigigo.orchextra.core.R.id.ox_toolbar) as Toolbar

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    toolbar.setNavigationOnClickListener { onBackPressed() }

    title = getString(R.string.ox_imagerecognizer_title)
  }

  public override fun onResume() {
    super.onResume()
    imageRecognizerActivity = this
  }

  public override fun onDestroy() {
    super.onDestroy()
    imageRecognizerActivity = null
  }

  private fun getLicenseKey(): String = intent.getStringExtra(LICENSE_KEY)

  private fun getClientAccessKey(): String = intent.getStringExtra(CLIENT_ACCESS_KEY)

  private fun getClientSecretKey(): String = intent.getStringExtra(CLIENT_SECRET_KEY)

  private fun showResponseCode(code: String) {
    codeTv.text = "CODE= $code"
    sendBroadcast(TriggerBroadcastReceiver.getTriggerIntent(TriggerType.IMAGE_RECOGNITION withValue code))

  }

  private fun startVuforia() {
    var imageRecognition = ImageRecognitionVuforiaImpl()

    val contextProvider = object : ContextProvider {
      override fun getApplicationContext(): Context = this@ImageRecognizerActivity.application.applicationContext

      override fun getCurrentActivity(): Activity = this@ImageRecognizerActivity

      override fun isApplicationContextAvailable(): Boolean = true

      override fun isActivityContextAvailable(): Boolean = true
    }

    imageRecognition.setContextProvider(contextProvider)

    val vuforiaCredentials = object : ImageRecognitionCredentials {
      override fun getLicensekey(): String = this@ImageRecognizerActivity.getLicenseKey()

      override fun getClientSecretKey(): String = this@ImageRecognizerActivity.getClientSecretKey()

      override fun getClientAccessKey(): String = this@ImageRecognizerActivity.getClientAccessKey()
    }

    imageRecognition.startImageRecognition(vuforiaCredentials)
  }

  companion object Navigator {
    private val LICENSE_KEY = "license_key"
    private val CLIENT_ACCESS_KEY = "client_access_key"
    private val CLIENT_SECRET_KEY = "client_secret_key"
    private var imageRecognizerActivity: ImageRecognizerActivity? = null

    fun open(context: Context, licenseKey: String, clientAccessKey: String,
        clientSecretKey: String) {
      val intent = Intent(context, ImageRecognizerActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      intent.putExtra(LICENSE_KEY, licenseKey)
      intent.putExtra(CLIENT_ACCESS_KEY, clientAccessKey)
      intent.putExtra(CLIENT_SECRET_KEY, clientSecretKey)
      context.startActivity(intent)
    }

    fun showCode(code: String) {
      imageRecognizerActivity?.showResponseCode(code)
    }

    fun finish() {
      imageRecognizerActivity?.finish()
    }
  }
}