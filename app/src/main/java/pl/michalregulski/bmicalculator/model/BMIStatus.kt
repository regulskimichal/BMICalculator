package pl.michalregulski.bmicalculator.model

import pl.michalregulski.bmicalculator.R

enum class BMIStatus(
    val descriptionId: Int,
    val colorId: Int
) {

    OBESITY(R.string.obesity, R.color.obesity),
    OVERWEIGHT(R.string.overweight, R.color.overweight),
    NORMAL(R.string.normalRange, R.color.normalRange),
    UNDERWEIGHT(R.string.underweight, R.color.underweight)

}
