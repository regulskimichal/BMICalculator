package pl.michalregulski.bmicalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*

class About : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { onClickFab() }
    }

    private fun onClickFab() {
        val mail =
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "regulskimichal@outlook.com", null))
                .putExtra(Intent.EXTRA_EMAIL, "regulskimichal@outlook.com")
                .putExtra(Intent.EXTRA_SUBJECT, "Question from BMICalculator")
        startActivity(mail)
    }

}
