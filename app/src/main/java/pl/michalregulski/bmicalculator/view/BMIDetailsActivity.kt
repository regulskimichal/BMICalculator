package pl.michalregulski.bmicalculator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmi_details.*
import pl.michalregulski.bmicalculator.Application.Companion.bmiStatusKey
import pl.michalregulski.bmicalculator.R
import pl.michalregulski.bmicalculator.model.BMIStatus

class BMIDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bmi_details)
        setSupportActionBar(toolbar)

        val bmiStatus = intent.getSerializableExtra(bmiStatusKey)
        if (bmiStatus is BMIStatus) {
            setTitle(bmiStatus.descriptionId)
        }
    }

}
