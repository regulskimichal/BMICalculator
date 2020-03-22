package pl.michalregulski.bmicalculator.model

import pl.michalregulski.bmicalculator.R

enum class BMICalculatorType(
    val weightDescriptionId: Int,
    val heightDescriptionId: Int
) {

    IMPERIAL(R.string.imperialWeightUnit, R.string.imperialHeightUnit),
    METRIC(R.string.metricWeightUnit, R.string.metricHeightUnit)

}
