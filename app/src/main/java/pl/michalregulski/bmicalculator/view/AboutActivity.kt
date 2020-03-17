package pl.michalregulski.bmicalculator.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import pl.michalregulski.bmicalculator.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.setOnClickListener { onClickFab() }
    }

    private fun onClickFab() {
        val mail = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", EMAIL_ADDRESS, null))
            .putExtra(Intent.EXTRA_EMAIL, EMAIL_ADDRESS)
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject))

        startActivity(mail)
    }

    companion object {
        private const val EMAIL_ADDRESS = "regulskimichal@outlook.com"
    }

}
