package pl.michalregulski.bmicalculator

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pl.michalregulski.bmicalculator.logic.BMICalculator
import pl.michalregulski.bmicalculator.logic.ImperialBMICalculator
import pl.michalregulski.bmicalculator.logic.MetricBMICalculator

class MainActivity : AppCompatActivity() {

    private val metricBMICalc: BMICalculator = MetricBMICalculator()
    private val imperialBMICalc: BMICalculator = ImperialBMICalculator()

    private var currentBMICalc: BMICalculator = metricBMICalc
    private var isCorrectWeight = false
    private var isCorrectHeight = false
    private var isMetric = false
    private var bmi = 0.0

    private lateinit var menu: Menu
    private lateinit var preferences: SharedPreferences
    private lateinit var preferencesEditor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        preferences = getPreferences(0)
        if (savedInstanceState != null) {
            isMetric = savedInstanceState.getBoolean("isMetric")
            setCalculator(isMetric)
            isCorrectWeight = savedInstanceState.getBoolean("isCorrectWeight")
            isCorrectHeight = savedInstanceState.getBoolean("isCorrectHeight")
            bmi = savedInstanceState.getDouble("bmi")
            setTextViews(bmi)
        } else {
            isMetric = preferences.getBoolean("isMetric", true)
            setCalculator(isMetric)
            weight_ET.setText(preferences.getString("weight", ""))
            height_ET.setText(preferences.getString("height", ""))
            metric_imperial_S.isChecked = !isMetric
        }

        weight_ET.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            weightWatcher()
        }

        height_ET.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            heightWatcher()
        }

        bmi_value_TV.doOnTextChanged { _: CharSequence?, _: Int, _: Int, _: Int ->
            bmiWatcher()
        }

        metric_imperial_S.setOnClickListener {
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
        setSaveButtonStatus()
        setShareButtonStatus()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                persistValues()
                Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.share -> {
                val textToShare: String = String.format(
                    resources.configuration.locales[0],
                    "%s %.2f. %s %s. %s.",
                    getString(R.string.my_bmi),
                    bmi,
                    getString(R.string.i_am),
                    about(bmi).toLowerCase(),
                    getString(R.string.sent_via_bmi_calc)
                )
                val share = Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, textToShare)
                    .setType(MIMETYPE_TEXT_PLAIN)

                startActivity(share)
                true
            }
            R.id.clear -> {
                clearAll()
                persistValues()
                Toast.makeText(applicationContext, R.string.cleared, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.about -> {
                val about = Intent(applicationContext, About::class.java)
                startActivity(about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isCorrectWeight", isCorrectWeight)
        outState.putBoolean("isCorrectHeight", isCorrectHeight)
        outState.putBoolean("isMetric", isMetric)
        outState.putDouble("bmi", bmi)
    }

    private fun weightWatcher() {
        validateWeight(weight_ET.text.toString())
        setSaveButtonStatus()
    }

    private fun heightWatcher() {
        validateHeight(height_ET.text.toString())
        setSaveButtonStatus()
    }

    private fun bmiWatcher() {
        setShareButtonStatus()
    }

    private fun switchClickListener() {
        setCalculator(!metric_imperial_S.isChecked)
        validateWeight(weight_ET.text.toString())
        validateHeight(height_ET.text.toString())
        setSaveButtonStatus()
    }

    private fun calculateBMI() {
        val weightString = weight_ET.text.toString()
        val heightString = height_ET.text.toString()
        bmi = 0.0
        if (isCorrectHeight && isCorrectWeight) {
            val weight = weightString.toDouble()
            val height = heightString.toDouble()
            bmi = currentBMICalc.calculateBMI(weight, height)
        }
        setTextViews(bmi)
    }

    private fun validateWeight(weightString: String) {
        if (weightString != "") {
            val weight = weightString.toDouble()
            isCorrectWeight = currentBMICalc.isValidWeight(weight)
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
            isCorrectHeight = currentBMICalc.isValidHeight(height)
            if (isCorrectHeight) {
                height_ET.error = null
            } else {
                height_ET.error = getString(R.string.invalid_height)
            }
        } else {
            isCorrectHeight = false
        }
    }

    private fun setSaveButtonStatus() {
        menu.findItem(R.id.save).isEnabled = isCorrectWeight && isCorrectHeight
    }

    private fun setShareButtonStatus() {
        menu.findItem(R.id.share).isEnabled = bmi != 0.0
    }

    private fun clearAll() {
        weight_ET.setText("")
        height_ET.setText("")
        metric_imperial_S.isChecked = false
        bmi = 0.0
        setMetric()
        setTextViews(bmi)
    }

    private fun persistValues() {
        preferencesEditor = preferences.edit()
        preferencesEditor.putString("weight", weight_ET.text.toString())
        preferencesEditor.putString("height", height_ET.text.toString())
        preferencesEditor.putBoolean("isMetric", isMetric)
        preferencesEditor.apply()
    }

    private fun setCalculator(isMetric: Boolean) {
        if (isMetric) {
            setMetric()
        } else {
            setImperial()
        }
    }

    private fun setMetric() {
        isMetric = true
        currentBMICalc = metricBMICalc
        weight_unit_TV.setText(R.string.metric_weight_unit)
        height_unit_TV.setText(R.string.metric_height_unit)
    }

    private fun setImperial() {
        isMetric = false
        currentBMICalc = imperialBMICalc
        weight_unit_TV.setText(R.string.imperial_weight_unit)
        height_unit_TV.setText(R.string.imperial_height_unit)
    }

    private fun setTextViews(bmi: Double) {
        if (bmi == 0.0) {
            bmi_message_TV.text = ""
            bmi_value_TV.text = ""
            bmi_info_TV.text = ""
        } else {
            bmi_message_TV.setText(R.string.bmi_message)
            bmi_value_TV.text = getFormattedBMI(bmi)
            bmi_info_TV.text = about(bmi)
        }
    }

    private fun about(bmi: Double): String {
        val id = when {
            bmi > 30 -> R.string.obese
            bmi > 25 -> R.string.overweight
            bmi > 18.5 -> R.string.normal_range
            else -> R.string.underweight
        }

        return getString(id)
    }

    private fun getFormattedBMI(bmi: Double): String {
        return String.format(resources.configuration.locales[0], "%.2f", bmi)
    }

}
