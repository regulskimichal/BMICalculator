package pl.michalregulski.bmicalculator.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.content_bmi.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.michalregulski.bmicalculator.Application.Companion.bmiStatusKey
import pl.michalregulski.bmicalculator.R
import pl.michalregulski.bmicalculator.databinding.ActivityBmiBinding
import pl.michalregulski.bmicalculator.viewmodel.BMIViewModel

class BMIActivity : AppCompatActivity() {

    private val viewModel by viewModel<BMIViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityBmiBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        fab.setOnClickListener {
            calculateBMI()
        }

        bmiContentCL.setOnClickListener {
            getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                0
            )
        }

        bmiValueTV.setOnClickListener {
            val intent = Intent(this, BMIDetailsActivity::class.java)
                .putExtra(bmiStatusKey, viewModel.bmiStatus.get())

            startActivityForResult(intent, 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.unitSwitch)?.let {
            if (viewModel.isImperialUnits.get()) {
                it.setTitle(R.string.metricUnits)
            } else {
                it.setTitle(R.string.imperialUnits)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.unitSwitch -> {
                viewModel.isImperialUnits.set(!viewModel.isImperialUnits.get())
                true
            }
            R.id.about -> {
                val about = Intent(applicationContext, AboutActivity::class.java)
                startActivity(about)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun calculateBMI() {
        if (viewModel.isValidWeight.get() && viewModel.isValidHeight.get()) {
            val weight = viewModel.weight.get().toDouble()
            val height = viewModel.height.get().toDouble()
            val bmi = viewModel.bmiCalculator.get().calculateBMI(weight, height)
            viewModel.bmi.set(bmi)
            weightTIL.error = null
            weightTIL.isErrorEnabled = false
            heightTIL.error = null
            heightTIL.isErrorEnabled = false
            bmiValueTV.setTextColor(getColor(viewModel.color.get()))
        } else {
            viewModel.bmi.set(null)
            if (!viewModel.isValidWeight.get()) {
                weightTIL.error = getString(R.string.invalidWeight)
            }
            if (!viewModel.isValidHeight.get()) {
                heightTIL.error = getString(R.string.invalidHeight)
            }
        }
    }

}
