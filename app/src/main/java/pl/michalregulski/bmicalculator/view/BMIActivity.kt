package pl.michalregulski.bmicalculator.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.content_bmi.*
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
            weight_TIL.error = null
            weight_TIL.isErrorEnabled = false
            height_TIL.error = null
            height_TIL.isErrorEnabled = false
        } else {
            viewModel.bmi.set(null)
            when {
                !viewModel.isValidWeight.get() -> weight_TIL.error = getString(R.string.invalid_weight)
                !viewModel.isValidHeight.get() -> height_TIL.error = getString(R.string.invalid_height)
            }
        }
    }

}
