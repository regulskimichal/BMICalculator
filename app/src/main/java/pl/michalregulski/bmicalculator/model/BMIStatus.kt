package pl.michalregulski.bmicalculator.model

import pl.michalregulski.bmicalculator.R

enum class BMIStatus(
    val descriptionId: Int
) {

    OBESITY(R.string.obesity),
    OVERWEIGHT(R.string.overweight),
    NORMAL(R.string.normal_range),
    UNDERWEIGHT(R.string.underweight)

}
