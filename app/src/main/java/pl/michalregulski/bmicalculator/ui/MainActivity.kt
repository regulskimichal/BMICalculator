package pl.michalregulski.bmicalculator.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pl.michalregulski.bmicalculator.R
import pl.michalregulski.bmicalculator.domain.*

class MainActivity : AppCompatActivity() {

    private var bmiCalculatorType = BMICalculatorType.METRIC
    private var bmiCalculator = BMICalculator.get(bmiCalculatorType)

    private var bmi = 0.0
    private var isCorrectWeight = false
    private var isCorrectHeight = false

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        weight_ET.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            weightWatcher()
        }

        height_ET.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            heightWatcher()
        }

        bmi_calculator_type_switch.setOnClickListener {
            switchClickListener()
        }

        fab.setOnClickListener {
            calculateBMI()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu == null) {
            return false
        }

        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                val about = Intent(applicationContext, About::class.java)
                startActivity(about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun weightWatcher() {
        validateWeight(weight_ET.text.toString())
    }

    private fun heightWatcher() {
        validateHeight(height_ET.text.toString())
    }

    private fun switchClickListener() {
        val bmiCalculatorType = if (bmi_calculator_type_switch.isChecked) {
            BMICalculatorType.IMPERIAL
        } else {
            BMICalculatorType.METRIC
        }
        setCalculatorType(bmiCalculatorType)
        validateWeight(weight_ET.text.toString())
        validateHeight(height_ET.text.toString())
    }

    private fun calculateBMI() {
        val weightString = weight_ET.text.toString()
        val heightString = height_ET.text.toString()
        bmi = 0.0
        if (isCorrectHeight && isCorrectWeight) {
            val weight = weightString.toDouble()
            val height = heightString.toDouble()
            bmi = bmiCalculator.calculateBMI(weight, height)
        }
        setTextViews(bmi)
    }

    private fun validateWeight(weightString: String) {
        if (weightString != "") {
            val weight = weightString.toDouble()
            isCorrectWeight = bmiCalculator.isValidWeight(weight)
            if (isCorrectWeight) {
                weight_ET.error = null
            } else {
                weight_ET.error = getString(R.string.invalid_weight)
            }
        } else {
            isCorrectWeight = false
        }
    }

    private fun validateHeight(heightString: String) {
        if (heightString != "") {
            val height = heightString.toDouble()
            isCorrectHeight = bmiCalculator.isValidHeight(height)
            if (isCorrectHeight) {
                height_ET.error = null
            } else {
                height_ET.error = getString(R.string.invalid_height)
            }
        } else {
            isCorrectHeight = false
        }
    }

    private fun setCalculatorType(bmiCalculatorType: BMICalculatorType) {
        this.bmiCalculatorType = bmiCalculatorType
        this.bmiCalculator = BMICalculator.get(bmiCalculatorType)
        weight_unit_TV.setText(bmiCalculatorType.weightDescriptionId)
        height_unit_TV.setText(bmiCalculatorType.heightDescriptionId)
    }

    private fun setTextViews(bmi: Double) {
        if (bmi == 0.0) {
            bmi_message_TV.text = ""
            bmi_value_TV.text = ""
            bmi_info_TV.text = ""
        } else {
            bmi_message_TV.setText(R.string.bmi_message)
            bmi_value_TV.text = getFormattedBMI(bmi)
            bmi_info_TV.setText(BMICalculator.getDescription(bmi).descriptionId)
        }
    }

    private fun getFormattedBMI(bmi: Double): String {
        return String.format(resources.configuration.locales[0], "%.2f", bmi)
    }

}
