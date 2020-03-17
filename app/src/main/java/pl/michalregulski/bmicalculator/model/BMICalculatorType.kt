package pl.michalregulski.bmicalculator.model

import pl.michalregulski.bmicalculator.R

enum class BMICalculatorType(
    val weightDescriptionId: Int,
    val heightDescriptionId: Int
) {

    IMPERIAL(R.string.imperial_weight_unit, R.string.imperial_height_unit),
    METRIC(R.string.metric_weight_unit, R.string.metric_height_unit)

}
