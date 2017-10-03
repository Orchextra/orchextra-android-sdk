package com.gigigo.orchextra.imagerecognizer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.gigigo.orchextra.core.domain.entities.ImageRecognizerCredentials

class ImageRecognizerActivity : AppCompatActivity() {

  private lateinit var licenseKeyTv: TextView
  private lateinit var accessKeyTv: TextView
  private lateinit var secretKeyTv: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_imagerecognizer)

    initViews()

    val credentials = intent.getParcelableExtra<ImageRecognizerCredentials>(CONFIG_EXTRA)
    credentials?.let {
      licenseKeyTv.text = "license key: ${credentials.licenseKey}"
      accessKeyTv.text = "access key: ${credentials.clientAccessKey}"
      secretKeyTv.text = "secret key: ${credentials.clientSecretKey}"
    }
  }

  private fun initViews() {
    initToolbar()

    licenseKeyTv = findViewById(R.id.imagerecognizer_license_tv) as TextView
    accessKeyTv = findViewById(R.id.imagerecognizer_accessKey_tv) as TextView
    secretKeyTv = findViewById(R.id.imagerecognizer_secretKey_tv) as TextView
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

  companion object Navigator {
    private val CONFIG_EXTRA = "config_extra"
    private var imageRecognizerActivity: ImageRecognizerActivity? = null

    fun open(context: Context, credentials: ImageRecognizerCredentials) {
      val intent = Intent(context, ImageRecognizerActivity::class.java)
      intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      intent?.putExtra(CONFIG_EXTRA, credentials)
      context.startActivity(intent)
    }

    fun finish() {
      imageRecognizerActivity?.finish()
    }
  }
}