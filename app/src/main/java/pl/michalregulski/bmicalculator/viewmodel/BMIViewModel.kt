package pl.michalregulski.bmicalculator.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import pl.michalregulski.bmicalculator.R
import pl.michalregulski.bmicalculator.model.BMICalculator
import pl.michalregulski.bmicalculator.model.BMICalculatorType
import pl.michalregulski.bmicalculator.model.BMIStatus

class BMIViewModel : ViewModel() {

    val weight = ObservableString("")

    val height = ObservableString("")

    val isImperialUnits = ObservableBoolean(false)

    val bmiCalculatorType = object : NonNullObservableField<BMICalculatorType>(isImperialUnits) {
        override fun get() = if (isImperialUnits.get()) BMICalculatorType.IMPERIAL else BMICalculatorType.METRIC
    }

    val bmiCalculator = object : NonNullObservableField<BMICalculator>(bmiCalculatorType) {
        override fun get() = BMICalculator.get(bmiCalculatorType.get())
    }

    val bmi = ObservableField<Double?>()

    val bmiString = object : ObservableString(bmi) {
        override fun get() = if (bmi.get() != null) String.format("%.2f", bmi.get()) else ""
    }

    val description = object : ObservableInt(bmi) {
        override fun get(): Int {
            val bmi = bmi.get()
            return if (bmi != null) getDescription(bmi).descriptionId else R.string.empty
        }

        private fun getDescription(bmi: Double): BMIStatus = when {
            bmi >= 30.0 -> BMIStatus.OBESITY
            bmi >= 25.0 -> BMIStatus.OVERWEIGHT
            bmi >= 18.5 -> BMIStatus.NORMAL
            else -> BMIStatus.UNDERWEIGHT
        }
    }

    val isResultVisible = object : ObservableBoolean(bmi) {
        override fun get() = bmi.get() != null
    }

    val isValidWeight = object : ObservableBoolean(bmiCalculator, weight) {
        override fun get() =
            if (weight.get().isNotEmpty()) bmiCalculator.get().isValidWeight(weight.get().toDouble()) else false
    }

    val isValidHeight = object : ObservableBoolean(bmiCalculator, height) {
        override fun get() =
            if (height.get().isNotEmpty()) bmiCalculator.get().isValidHeight(height.get().toDouble()) else false
    }

}
